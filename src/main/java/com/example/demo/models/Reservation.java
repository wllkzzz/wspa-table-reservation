package com.example.demo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Table table;

    private LocalDateTime reservationTime;
}
