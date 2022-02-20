package com.example.jwtlesson2.controller;

import com.example.jwtlesson2.DTO.IncomeDto;
import com.example.jwtlesson2.DTO.OutComeDto;
import com.example.jwtlesson2.DTO.Result;
import com.example.jwtlesson2.entity.Outcome;
import com.example.jwtlesson2.service.OutComeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/outcome")
public class OutComeController {

    @Autowired
    OutComeService outComeService;

    @GetMapping
    public HttpEntity<?> getAllIncomes(@RequestParam int page) {
        Page<Outcome> outcomes = outComeService.getAllOutComes(page);
        return ResponseEntity.ok(outcomes);
    }

    @PostMapping("/transfer")
    public HttpEntity<?> addOutCome(@RequestBody OutComeDto outComeDto) {
        Result result = outComeService.addOutCome(outComeDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.NOT_ACCEPTABLE).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteOutComeById(@PathVariable Integer id) {
        Result result = outComeService.deleteOutComeById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_ACCEPTABLE).body(result);
    }
}
