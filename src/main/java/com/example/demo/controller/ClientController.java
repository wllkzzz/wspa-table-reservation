package com.example.demo.controller;

import com.example.demo.models.Client;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private static final Logger logger = LogManager.getLogger(ClientController.class);

    private final ClientService clientService;

    @GetMapping
    public List<Client> getAllClients() {
        logger.info("Fetching all clients");
        return clientService.getAllClients();
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        logger.info("Creating new client: {}", client.getName());
        return clientService.createClient(client);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        logger.info("Updating client with ID: {}", client.getId());
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        logger.warn("Deleting client with ID: {}", id);
        clientService.deleteClient(id);
    }
}
