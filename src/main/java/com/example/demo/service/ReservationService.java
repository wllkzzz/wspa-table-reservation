package com.example.demo.service;

import com.example.demo.models.Client;
import com.example.demo.models.Reservation;
import com.example.demo.models.Table;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.TableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final TableRepository tableRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(String name, String phone, String email, int numberOfPeople, LocalDateTime reservationTime) {
        Client client = clientRepository.findByEmail(email).orElseGet(() -> {
            Client newClient = new Client();
            newClient.setName(name);
            newClient.setPhone(phone);
            newClient.setEmail(email);
            return clientRepository.save(newClient);
        });

        Table table = new Table();
        table.setSeats(numberOfPeople);
        table.setStatus("Reserved");
        Table savedTable = tableRepository.save(table);

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setTable(savedTable);
        reservation.setReservationTime(reservationTime);
        reservation.setNumberOfPeople(numberOfPeople);

        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id, int numberOfPeople, LocalDateTime reservationTime) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        Table table = reservation.getTable();
        table.setSeats(numberOfPeople);
        tableRepository.save(table);

        reservation.setNumberOfPeople(numberOfPeople);
        reservation.setReservationTime(reservationTime);

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        tableRepository.delete(reservation.getTable());

        reservationRepository.delete(reservation);
    }
}