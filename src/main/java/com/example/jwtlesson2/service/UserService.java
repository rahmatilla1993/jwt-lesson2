package com.example.jwtlesson2.service;

import com.example.jwtlesson2.DTO.Result;
import com.example.jwtlesson2.entity.Users;
import com.example.jwtlesson2.enums.Element;
import com.example.jwtlesson2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    Element message = Element.USER;

    @Autowired
    UserRepository userRepository;

    public Page<Users> getAllUsers(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return userRepository.findAll(pageable);
    }

    public Result getUserById(Integer id) {
        Optional<Users> optionalUsers = userRepository.findById(id);
        return optionalUsers.map(users -> new Result(true, users)).orElseGet(() -> new Result(message.getMessageNotFound(), false));
    }

    private Result addingUser(Users user, boolean create, boolean edit, Integer id) {
        Users new_user = new Users();
        if (create && userRepository.existsByUserName(user.getUserName())) {
            return new Result(message.getMessageExists(), false);
        }
        new_user.setUserName(user.getUserName());
        new_user.setFirstName(user.getFirstName());
        new_user.setPassword(user.getPassword());
        new_user.setLastName(user.getLastName());
        return new Result(true, new_user);
    }

    public Result addUser(Users user) {
        Result result = addingUser(user, true, false, null);
        if (result.isSuccess()) {
            Users users = (Users) result.getObject();
            userRepository.save(users);
            return new Result(message.getMessageAdded(), true);
        }
        return result;
    }

    public Result editUserById(Users user,Integer id){
        Optional<Users> optionalUsers = userRepository.findById(id);
        if(optionalUsers.isPresent()) {
            Result result = addingUser(user, false, true, id);
            if(result.isSuccess()){
                Users editUser = optionalUsers.get();
                Users new_user= (Users) result.getObject();
                editUser.setUserName(new_user.getUserName());
                editUser.setPassword(new_user.getPassword());
                editUser.setFirstName(new_user.getFirstName());
                editUser.setLastName(new_user.getLastName());
                userRepository.save(editUser);
                return new Result(message.getMessageEdited(), true);
            }
            return result;
        }
        return new Result(message.getMessageNotFound(), false);
    }

    public Result deleteUserById(Integer id){
        Optional<Users> optionalUsers = userRepository.findById(id);
        if(optionalUsers.isPresent()){
            userRepository.delete(optionalUsers.get());
            return new Result(message.getMessageDeleted(),true);
        }
        return new Result(message.getMessageNotFound(), false);
    }
}
