package com.example.jwtlesson2.controller;

import com.example.jwtlesson2.DTO.Result;
import com.example.jwtlesson2.entity.Users;
import com.example.jwtlesson2.enums.Element;
import com.example.jwtlesson2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class UserController {

    Element message = Element.USER;

    @Autowired
    UserService userService;

    @GetMapping
    public HttpEntity<?> getAllUsers(@RequestParam int page) {
        Page<Users> users = userService.getAllUsers(page);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getUserById(@PathVariable Integer id) {
        Result result = userService.getUserById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping
    public HttpEntity<?> AddUser(@RequestBody Users user) {
        Result result = userService.addUser(user);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.NOT_ACCEPTABLE).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editUserById(@PathVariable Integer id, @RequestBody Users users) {
        Result result = userService.editUserById(users, id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(message.getMessageNotFound()) ?
                HttpStatus.NOT_FOUND : HttpStatus.NOT_ACCEPTABLE).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUserById(@PathVariable Integer id) {
        Result result = userService.deleteUserById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
