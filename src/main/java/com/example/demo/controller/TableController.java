package com.example.demo.controller;

import com.example.demo.models.Table;
import com.example.demo.service.TableService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableController {

    private static final Logger logger = LogManager.getLogger(TableController.class);

    private final TableService tableService;

    @GetMapping("/{id}")
    public Table getTable(@PathVariable Long id) {
        logger.info("Fetching table with ID: {}", id);
        return tableService.getTableById(id);
    }

    @PostMapping
    public Table createTable(@RequestBody Table table) {
        logger.info("Creating new table with seats: {}", table.getSeats());
        return tableService.createTable(table);
    }

    @PutMapping("/{id}")
    public Table updateTable(@PathVariable Long id, @RequestBody Table table) {
        logger.info("Updating table with ID: {}", id);
        return tableService.updateTable(id, table);
    }

    @DeleteMapping("/{id}")
    public void deleteTable(@PathVariable Long id) {
        logger.warn("Deleting table with ID: {}", id);
        tableService.deleteTable(id);
    }
}
