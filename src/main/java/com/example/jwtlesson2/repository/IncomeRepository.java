package com.example.jwtlesson2.repository;

import com.example.jwtlesson2.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {

    @Procedure
    boolean get_TransAction(Double amount, Double balance, Integer from_user_id, Integer to_user_id);

    /*@Query(value = "SELECT COUNT(*) FROM card INNER JOIN users u ON card.users_id = u.id\n" +
            "    WHERE u.id = ?1 AND card.id = ?2;", nativeQuery = true)
    Integer findByUser_IdAndCard_Id(Integer id, Integer card_id);*/
}
