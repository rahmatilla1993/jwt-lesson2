package com.example.jwtlesson2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//Berilgan id li user boshqa kartaga pul o'tkazadi
public class Outcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Card from_card_id;

    @ManyToOne
    private Card to_card_id;

    private Double amount;

    private Date date;

    private Double commission_amount;
}
