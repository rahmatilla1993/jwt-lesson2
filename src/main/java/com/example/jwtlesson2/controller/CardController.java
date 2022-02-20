package com.example.jwtlesson2.controller;

import com.example.jwtlesson2.DTO.CardDto;
import com.example.jwtlesson2.DTO.Result;
import com.example.jwtlesson2.entity.Card;
import com.example.jwtlesson2.enums.Element;
import com.example.jwtlesson2.service.CardService;
import com.sun.istack.Interned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    Element messageCard = Element.CARD;

    @Autowired
    CardService cardService;

    @GetMapping
    public HttpEntity<?> getAllCards(@RequestParam int page) {
        Page<Card> cards = cardService.getAllCards(page);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCardById(@PathVariable Integer id) {
        Result result = cardService.getCardById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/byUserId/{user_id}")
    public HttpEntity<?> getCardsByUserId(@PathVariable Integer user_id) {
        List<Result> results = cardService.getCardsByUserId(user_id);
        return ResponseEntity.status(results.get(0).isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(results);
    }

    @PostMapping("/add")
    public HttpEntity<?> addCard(@RequestBody CardDto cardDto) {
        Result result = cardService.addCard(cardDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : result.getMessage().equals(messageCard.getMessageExists()) ?
                HttpStatus.NOT_ACCEPTABLE : HttpStatus.NOT_FOUND).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCardById(@PathVariable Integer id, @RequestBody CardDto cardDto) {
        Result result = cardService.editCardById(id, cardDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(messageCard.getMessageExists()) ?
                HttpStatus.NOT_ACCEPTABLE : HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCardById(@PathVariable Integer id) {
        Result result = cardService.deleteCardById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
