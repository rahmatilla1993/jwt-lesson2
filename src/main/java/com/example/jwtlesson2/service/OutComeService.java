package com.example.jwtlesson2.service;

import com.example.jwtlesson2.DTO.OutComeDto;
import com.example.jwtlesson2.DTO.Result;
import com.example.jwtlesson2.entity.Card;
import com.example.jwtlesson2.entity.History;
import com.example.jwtlesson2.entity.Outcome;
import com.example.jwtlesson2.entity.Users;
import com.example.jwtlesson2.enums.Element;
import com.example.jwtlesson2.repository.CardRepository;
import com.example.jwtlesson2.repository.HistoryRepository;
import com.example.jwtlesson2.repository.OutComeRepository;
import com.example.jwtlesson2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OutComeService {

    Element messageCard = Element.CARD;
    Element messageUser = Element.USER;

    @Autowired
    OutComeRepository outComeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    HistoryRepository historyRepository;

    public Page<Outcome> getAllOutComes(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return outComeRepository.findAll(pageable);
    }

    public Result addOutCome(OutComeDto outComeDto) {
        Outcome outcome = new Outcome();

        Optional<Card> optional_from_Card = cardRepository.findById(outComeDto.getFrom_card_id());
        Optional<Card> optional_to_Card = cardRepository.findById(outComeDto.getTo_card_id());
        Optional<Users> optionalUser = userRepository.findById(outComeDto.getUser_id());

        if (!optionalUser.isPresent()) {
            return new Result(messageUser.getMessageNotFound(), false);
        }
        if (!(optional_from_Card.isPresent() && optional_to_Card.isPresent())) {
            return new Result(messageCard.getMessageNotFound(), false);
        }

        Users user = optionalUser.get();
        Card from_card = optional_from_Card.get();
        Card to_card = optional_to_Card.get();

        if (!cardRepository.existsByUsers_IdAndId(user.getId(), from_card.getId())) {
            return new Result("This card does not belong to this user", false);
        }
        if (!(from_card.isActive() && to_card.isActive())) {
            return new Result("The card is not active", false);
        }

        Date date = new Date();
        if (date.after(from_card.getExpiredDate()) || date.after(to_card.getExpiredDate())) {
            return new Result("Card expired date", false);
        }

        if (outComeDto.getCommission_amount() < outComeDto.getAmount()) {
            return new Result("Insufficient commission amount", false);
        }

        boolean isDone = outComeRepository.get_TransAction(outComeDto.getAmount(), from_card.getBalance(),
                from_card.getId(), to_card.getId());
        if (isDone) {
            outcome.setAmount(outComeDto.getAmount());
            outcome.setDate(outComeDto.getDate());
            outcome.setFrom_card_id(from_card);
            outcome.setTo_card_id(to_card);
            outcome.setUser(user);
            Outcome saved_outcome = outComeRepository.save(outcome);
            historyOutcome(from_card, saved_outcome);
            return new Result("The transaction was successful", true, outcome);
        }
        return new Result("There is not enough money on the card for the transaction", false);
    }

    private void historyOutcome(Card from_card, Outcome saved_outcome) {
        History history = new History();
        history.setOutcome(saved_outcome);
        history.setCard(from_card);
        historyRepository.save(history);
    }

    public Result deleteOutComeById(Integer id) {
        Optional<Outcome> optionalOutcome = outComeRepository.findById(id);
        if (optionalOutcome.isPresent()) {
            outComeRepository.delete(optionalOutcome.get());
            return new Result("Outcome deleted", true);
        }
        return new Result("Outcome not found", false);
    }
}
