package com.example.demo.controller;

import com.example.demo.dto.ReservationRequest;
import com.example.demo.models.Reservation;
import com.example.demo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private static final Logger logger = LogManager.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        logger.info("Fetching all reservations");
        return reservationService.getAllReservations();
    }

    @PostMapping
    public Reservation createReservation(@RequestBody ReservationRequest reservationRequest) {
        logger.info("Creating new reservation for: {}", reservationRequest.getName());
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
        logger.debug("Updating reservation with ID: {}", id);
        return reservationService.updateReservation(
                id,
                reservationRequest.getNumberOfPeople(),
                reservationRequest.getReservationTime()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        logger.warn("Deleting reservation with ID: {}", id);
        reservationService.deleteReservation(id);
    }
}
