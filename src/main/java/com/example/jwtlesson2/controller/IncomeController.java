package com.example.jwtlesson2.controller;

import com.example.jwtlesson2.DTO.IncomeDto;
import com.example.jwtlesson2.DTO.Result;
import com.example.jwtlesson2.entity.Income;
import com.example.jwtlesson2.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @GetMapping
    public HttpEntity<?> getAllIncomes(@RequestParam int page) {
        Page<Income> incomePage = incomeService.getAllIncomes(page);
        return ResponseEntity.ok(incomePage);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getIncomeById(@PathVariable Integer id) {
        Result result = incomeService.getIncomeById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping("/transfer")
    public HttpEntity<?> addIncome(@RequestBody IncomeDto incomeDto) {
        Result result = incomeService.addIncome(incomeDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.NOT_ACCEPTABLE).body(result.getMessage());
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteIncomeById(@PathVariable Integer id) {
        Result result = incomeService.deleteIncomeById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
