package com.example.jwtlesson2.repository;

import com.example.jwtlesson2.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

    boolean existsByUserName(String userName);
}
