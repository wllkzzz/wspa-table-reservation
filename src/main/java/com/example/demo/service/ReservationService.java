package com.example.demo.service;

import com.example.demo.models.Client;
import com.example.demo.models.Reservation;
import com.example.demo.models.Table;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.TableRepository;
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
}
