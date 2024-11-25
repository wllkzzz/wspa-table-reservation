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

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody ReservationRequest reservationRequest) {
        return reservationService.updateReservation(
                id,
                reservationRequest.getNumberOfPeople(),
                reservationRequest.getReservationTime()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}