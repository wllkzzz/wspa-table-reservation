package com.example.demo.controller;

import com.example.demo.dto.ReservationRequest;
import com.example.demo.models.Reservation;
import com.example.demo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public Reservation createReservation(@RequestBody ReservationRequest reservationRequest) {
        return reservationService.createReservation(
                reservationRequest.getName(),
                reservationRequest.getPhone(),
                reservationRequest.getEmail(),
                reservationRequest.getNumberOfPeople(),
                reservationRequest.getReservationTime()
        );
    }
}
