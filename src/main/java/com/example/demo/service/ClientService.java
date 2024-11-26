package com.example.demo.service;

import com.example.demo.models.Client;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private static final Logger logger = LogManager.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        logger.info("Fetching all clients");
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        logger.debug("Fetching client with ID: {}", id);
        return clientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Client not found with ID: {}", id);
                    return new IllegalArgumentException("Client not found");
                });
    }

    public Client createClient(Client client) {
        logger.info("Creating new client");
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client updatedClient) {
        logger.info("Updating client with ID: {}", id);
        Client client = getClientById(id);
        client.setName(updatedClient.getName());
        client.setEmail(updatedClient.getEmail());
        client.setPhone(updatedClient.getPhone());
        logger.debug("Updated client details: {}", client);
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        logger.warn("Deleting client with ID: {}", id);
        clientRepository.deleteById(id);
    }
}
