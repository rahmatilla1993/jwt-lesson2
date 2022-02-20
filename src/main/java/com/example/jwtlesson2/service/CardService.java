package com.example.jwtlesson2.service;

import com.example.jwtlesson2.DTO.CardDto;
import com.example.jwtlesson2.DTO.Result;
import com.example.jwtlesson2.entity.Card;
import com.example.jwtlesson2.entity.Users;
import com.example.jwtlesson2.enums.Element;
import com.example.jwtlesson2.repository.CardRepository;
import com.example.jwtlesson2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    Element message = Element.CARD;

    Element messageUser = Element.USER;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    public Page<Card> getAllCards(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return cardRepository.findAll(pageable);
    }

    public Result getCardById(Integer id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        return optionalCard.map(card -> new Result(true, card)).orElseGet(() -> new Result(message.getMessageNotFound(), false));
    }

    public List<Result> getCardsByUserId(Integer user_id) {
        List<Result> results = new ArrayList<>();
        Optional<Users> optionalUsers = userRepository.findById(user_id);
        if (optionalUsers.isPresent()) {
            List<Users> users = cardRepository.findAllByUsers_Id(user_id);
            for (Users user : users) {
                Result result = new Result(true, user);
                results.add(result);
            }
            return results;
        }
        Result result = new Result(messageUser.getMessageNotFound(), false);
        results.add(result);
        return results;
    }

    private Result addingCard(CardDto cardDto, boolean create, boolean edit, Integer id) {
        Card card = new Card();
        if (create && cardRepository.existsByNumber(cardDto.getNumber()) ||
                edit && cardRepository.existsByIdIsNotAndNumber(id, cardDto.getNumber())) {
            return new Result(message.getMessageExists(), false);
        }
        Optional<Users> optionalUsers = userRepository.findById(cardDto.getUser_id());
        if (!optionalUsers.isPresent()) {
            return new Result(messageUser.getMessageNotFound(), false);
        }
        Users user = optionalUsers.get();
        card.setActive(cardDto.isActive());
        card.setBalance(cardDto.getBalance());
        card.setExpiredDate(cardDto.getExpiredDate());
        card.setNumber(cardDto.getNumber());
        card.setUsers(user);
        return new Result(true, card);
    }

    public Result addCard(CardDto cardDto) {
        Result result = addingCard(cardDto, true, false, null);
        if (result.isSuccess()) {
            Card card = (Card) result.getObject();
            cardRepository.save(card);
            return new Result(message.getMessageAdded(), true);
        }
        return result;
    }

    public Result editCardById(Integer id, CardDto cardDto) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()) {
            Result result = addingCard(cardDto, false, true, id);
            if (result.isSuccess()) {
                Card editCard = optionalCard.get();
                Card card = (Card) result.getObject();
                editCard.setUsers(card.getUsers());
                editCard.setBalance(card.getBalance());
                editCard.setNumber(card.getNumber());
                editCard.setActive(card.isActive());
                editCard.setExpiredDate(card.getExpiredDate());
                cardRepository.save(editCard);
                return new Result(message.getMessageEdited(), true);
            }
            return result;
        }
        return new Result(message.getMessageNotFound(), false);
    }

    public Result deleteCardById(Integer id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()) {
            cardRepository.delete(optionalCard.get());
            return new Result(message.getMessageDeleted(), true);
        }
        return new Result(message.getMessageNotFound(), false);
    }
}
