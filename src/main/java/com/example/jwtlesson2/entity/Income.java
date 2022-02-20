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
//Berilgan id li userni kartasiga pul o'tkaziladi
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * bitta userga bir nechta kirim bo'ladi
     */
    @ManyToOne
    private Users user;

    /**
     * Bitta karta ko'p kartaga yoki bitta kartaga pul o'tkazadi
     */
    @ManyToOne
    private Card from_card_id;

    /**
     * Bitta karta ko'p kartadan yoki bitta kartadan pul qabul qiladi
     */
    @ManyToOne
    private Card to_card_id;

    private Double amount;

    private Date date;
}
