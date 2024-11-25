package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequest {
    private String name;
    private String phone;
    private String email;
    private int numberOfPeople;
    private LocalDateTime reservationTime;
}
