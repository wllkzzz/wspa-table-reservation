package com.example.demo.repository;

import com.example.demo.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    int countByReservationTimeBetween(LocalDateTime start, LocalDateTime end);
}
