package com.example.jwtlesson2.service;

import com.example.jwtlesson2.entity.Users;
import com.example.jwtlesson2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    private List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        List<Users> userList = userRepository.findAll();
        for (Users user : userList) {
            String password = user.getPassword();
            String userName = user.getUserName();
            User new_user = new User(userName, passwordEncoder.encode(password), new ArrayList<>());
            users.add(new_user);
        }
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User not found");
    }
}
