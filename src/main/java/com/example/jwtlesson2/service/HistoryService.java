package com.example.jwtlesson2.service;

import com.example.jwtlesson2.DTO.Result;
import com.example.jwtlesson2.entity.Card;
import com.example.jwtlesson2.entity.History;
import com.example.jwtlesson2.enums.Element;
import com.example.jwtlesson2.repository.CardRepository;
import com.example.jwtlesson2.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    Element messageCard = Element.CARD;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    CardRepository cardRepository;

    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    public List<Result> getHistoriesByCardId(Integer card_id) {
        Optional<Card> optionalCard = cardRepository.findById(card_id);
        List<Result> results = new ArrayList<>();
        if (!optionalCard.isPresent()) {
            Result result = new Result(messageCard.getMessageNotFound(), false);
            results.add(result);
            return results;
        }
        Card card = optionalCard.get();
        List<History> histories = historyRepository.findAllByCard_Id(card.getId());
        for (History history : histories) {
            Result result = new Result(true, history);
            results.add(result);
        }
        return results;
    }
}
