package com.example.jwtlesson2.DTO;

import com.example.jwtlesson2.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDto {

    private Integer user_id;

    private Integer from_card_id;

    private Integer to_card_id;

    private Double amount;

    private Date date;
}
