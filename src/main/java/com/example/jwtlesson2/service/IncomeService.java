package com.example.jwtlesson2.service;

import com.example.jwtlesson2.DTO.IncomeDto;
import com.example.jwtlesson2.DTO.Result;
import com.example.jwtlesson2.entity.Card;
import com.example.jwtlesson2.entity.History;
import com.example.jwtlesson2.entity.Income;
import com.example.jwtlesson2.entity.Users;
import com.example.jwtlesson2.enums.Element;
import com.example.jwtlesson2.repository.CardRepository;
import com.example.jwtlesson2.repository.HistoryRepository;
import com.example.jwtlesson2.repository.IncomeRepository;
import com.example.jwtlesson2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class IncomeService {

    Element messageIncome = Element.IN_COME;
    Element messageCard = Element.CARD;
    Element messageUser = Element.USER;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryRepository historyRepository;

    public Page<Income> getAllIncomes(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return incomeRepository.findAll(pageable);
    }

    public Result getIncomeById(Integer id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        return optionalIncome.map(income -> new Result(true, income)).orElseGet(() -> new Result(messageIncome.getMessageNotFound(), false));
    }

    public Result addIncome(IncomeDto incomeDto) {
        Income income = new Income();

        Optional<Card> optionalFromCard = cardRepository.findById(incomeDto.getFrom_card_id());
        Optional<Card> optionalToCard = cardRepository.findById(incomeDto.getTo_card_id());
        Optional<Users> optionalUsers = userRepository.findById(incomeDto.getUser_id());

        if (!optionalUsers.isPresent()) {
            return new Result(messageUser.getMessageNotFound(), false);
        }
        if (!(optionalFromCard.isPresent() && optionalToCard.isPresent())) {
            return new Result(messageCard.getMessageNotFound(), false);
        }

        Users user = optionalUsers.get();
        Card from_card = optionalFromCard.get();
        Card to_card = optionalToCard.get();

        if (!cardRepository.existsByUsers_IdAndId(user.getId(), to_card.getId())) {
            return new Result("This card does not belong to this user", false);
        }
        if (!(from_card.isActive() && to_card.isActive())) {
            return new Result("The card is not active", false);
        }

        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String date_format = format.format(date);*/

        Date date = new Date();
        if (date.after(from_card.getExpiredDate()) || date.after(to_card.getExpiredDate())) {
            return new Result("Card expired date", false);
        }

        boolean isDone = incomeRepository.get_TransAction(incomeDto.getAmount(), from_card.getBalance(),
                from_card.getId(), to_card.getId());
        if (isDone) {
            income.setAmount(incomeDto.getAmount());
            income.setDate(incomeDto.getDate());
            income.setFrom_card_id(from_card);
            income.setTo_card_id(to_card);
            income.setUser(user);
            Income saved_income = incomeRepository.save(income);
            historyIncome(to_card,saved_income);
            return new Result("The transaction was successful", true, income);
        }
        return new Result("There is not enough money on the card for the transaction", false);
    }

    private void historyIncome(Card to_card, Income saved_income){
        History history = new History();
        history.setIncome(saved_income);
        history.setCard(to_card);
        historyRepository.save(history);
    }

    public Result deleteIncomeById(Integer id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (optionalIncome.isPresent()) {
            incomeRepository.delete(optionalIncome.get());
            return new Result("Income deleted", true);
        }
        return new Result("Income not found", false);
    }
}
