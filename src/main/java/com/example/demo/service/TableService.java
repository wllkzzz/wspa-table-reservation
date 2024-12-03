package com.example.demo.service;

import com.example.demo.models.Table;
import com.example.demo.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableService {

    private static final Logger logger = LogManager.getLogger(TableService.class);

    private final TableRepository tableRepository;

    public Table getTableById(Long id) {
        logger.debug("Fetching table with ID: {}", id);
        return tableRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Table not found with ID: {}", id);
                    return new IllegalArgumentException("Table not found");
                });
    }

    public Table createTable(Table table) {
        logger.info("Creating new table with seats: {}", table.getSeats());
        return tableRepository.save(table);
    }

    public void deleteTable(Long id) {
        logger.warn("Deleting table with ID: {}", id);
        if (tableRepository.existsById(id)) {
            tableRepository.deleteById(id);
            logger.info("Table with ID: {} has been deleted.", id);
        } else {
            logger.error("Table not found with ID: {}", id);
            throw new IllegalArgumentException("Table not found");
        }
    }

    public Table updateTable(Long id, Table updatedTable) {
        logger.info("Updating table with ID: {}", id);
        Table table = getTableById(id);
        table.setSeats(updatedTable.getSeats());
        table.setStatus(updatedTable.getStatus());
        logger.debug("Updated table details: {}", table);
        return tableRepository.save(table);
    }
}
