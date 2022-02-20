package com.example.jwtlesson2.controller;

import com.example.jwtlesson2.DTO.Result;
import com.example.jwtlesson2.entity.History;
import com.example.jwtlesson2.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @GetMapping
    public HttpEntity<?> getAllHistories() {
        List<History> histories = historyService.getAllHistory();
        return ResponseEntity.ok(histories);
    }

    @GetMapping("/byCardId/{card_id}")
    public HttpEntity<?> getIncomeHistoriesByCardId(@PathVariable Integer card_id) {
        List<Result> results = historyService.getHistoriesByCardId(card_id);
        return ResponseEntity.status(results.get(0).isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(results);
    }
}
