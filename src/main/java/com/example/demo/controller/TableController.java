package com.example.demo.controller;

import com.example.demo.models.Table;
import com.example.demo.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @GetMapping("/{id}")
    public Table getTable(@PathVariable Long id) {
        return tableService.getTableById(id);
    }

    @PostMapping
    public Table createTable(@RequestBody Table table) {
        return tableService.createTable(table);
    }

    @PutMapping("/{id}")
    public Table updateTable(@PathVariable Long id, @RequestBody Table table) {
        return tableService.updateTable(id, table);
    }

    @DeleteMapping("/{id}")
    public void deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
    }
}
