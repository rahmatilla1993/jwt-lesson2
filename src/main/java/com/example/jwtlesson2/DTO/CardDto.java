package com.example.jwtlesson2.DTO;

import com.example.jwtlesson2.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private Integer user_id;

    private Integer number;

    private Double balance;

    private Date expiredDate;

    boolean active = true;
}
