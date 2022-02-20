package com.example.jwtlesson2.repository;

import com.example.jwtlesson2.entity.Card;
import com.example.jwtlesson2.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {

    List<Users> findAllByUsers_Id(Integer users_id);

    boolean existsByNumber(Integer number);

    boolean existsByIdIsNotAndNumber(Integer id, Integer number);

    boolean existsByUsers_IdAndId(Integer users_id, Integer id);
}
