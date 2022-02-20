package com.example.jwtlesson2.repository;

import com.example.jwtlesson2.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History,Integer> {

    /*@Query(value = "SELECT id, income_id FROM history WHERE card_id = ?1;", nativeQuery = true)
    List<History> find_Income_Histories_By_CardId(Integer card_id);

    @Query(value = "SELECT id, outcome_id FROM history WHERE card_id = ?1;", nativeQuery = true)
    List<History> find_Outcome_Histories_By_CardId(Integer card_id);*/

    List<History> findAllByCard_Id(Integer card_id);
}
