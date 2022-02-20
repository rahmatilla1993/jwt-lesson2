package com.example.jwtlesson2.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class OutComeDto {

    private Integer user_id;

    private Integer from_card_id;

    private Integer to_card_id;

    private Double amount;

    private Date date;

    private Double commission_amount;
}
