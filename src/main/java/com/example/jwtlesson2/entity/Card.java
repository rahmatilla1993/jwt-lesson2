package com.example.jwtlesson2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Bitta userni bitta kartasi bo'ladi
     * bitta karta bitta userga tegishli
     */
    @ManyToOne
    private Users users;

    @Column(nullable = false,unique = true)
    private Integer number;

    private Double balance;

    private Date expiredDate;

    boolean active = true;
}
