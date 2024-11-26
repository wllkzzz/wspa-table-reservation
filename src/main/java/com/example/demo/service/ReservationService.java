package com.example.demo.service;

import com.example.demo.dto.ReservationNotification;
import com.example.demo.models.Client;
import com.example.demo.models.Reservation;
import com.example.demo.models.Table;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.TableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private static final Logger logger = LogManager.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final TableRepository tableRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public List<Reservation> getAllReservations() {
        logger.info("Fetching all reservations");
        return reservationRepository.findAll();
    }

    public Reservation createReservation(String name, String phone, String email, int numberOfPeople, LocalDateTime reservationTime) {
        logger.info("Creating reservation for client: {} with {} people", name, numberOfPeople);
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

        Reservation savedReservation = reservationRepository.save(reservation);

        messagingTemplate.convertAndSend(
                "/topic/reservations",
                new ReservationNotification("Reservation created", LocalDateTime.now(), savedReservation.getId())
        );

        logger.info("Reservation created with ID: {}", savedReservation.getId());
        return savedReservation;
    }

    public Reservation updateReservation(Long id, int numberOfPeople, LocalDateTime reservationTime) {
        logger.info("Updating reservation with ID: {}", id);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Reservation not found with ID: {}", id);
                    return new IllegalArgumentException("Reservation not found");
                });

        Table table = reservation.getTable();
        table.setSeats(numberOfPeople);
        tableRepository.save(table);

        reservation.setNumberOfPeople(numberOfPeople);
        reservation.setReservationTime(reservationTime);

        Reservation updatedReservation = reservationRepository.save(reservation);

        messagingTemplate.convertAndSend(
                "/topic/reservations",
                new ReservationNotification("Reservation updated", LocalDateTime.now(), updatedReservation.getId())
        );

        logger.debug("Reservation updated with {} people", numberOfPeople);
        return updatedReservation;
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        logger.warn("Deleting reservation with ID: {}", reservationId);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> {
                    logger.error("Reservation not found with ID: {}", reservationId);
                    return new IllegalArgumentException("Reservation not found");
                });

        tableRepository.delete(reservation.getTable());
        reservationRepository.delete(reservation);

        messagingTemplate.convertAndSend(
                "/topic/reservations",
                new ReservationNotification("Reservation deleted", LocalDateTime.now(), reservationId)
        );

        logger.info("Reservation with ID: {} has been deleted", reservationId);
    }
}
