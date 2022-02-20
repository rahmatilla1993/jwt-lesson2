package com.example.jwtlesson2.repository;

import com.example.jwtlesson2.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface OutComeRepository extends JpaRepository<Outcome,Integer> {

    @Procedure
    boolean get_TransAction(Double amount, Double balance, Integer from_user_id, Integer to_user_id);
}
