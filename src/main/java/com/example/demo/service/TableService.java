package com.example.demo.service;

import com.example.demo.models.Table;
import com.example.demo.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public Table getTableById(Long id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Table not found"));
    }

    public Table createTable(Table table) {
        return tableRepository.save(table);
    }

    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }

    public Table updateTable(Long id, Table updatedTable) {
        Table table = getTableById(id);
        table.setSeats(updatedTable.getSeats());
        table.setStatus(updatedTable.getStatus());
        return tableRepository.save(table);
    }
}
