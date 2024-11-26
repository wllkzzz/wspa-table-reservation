package com.example.demo.controller;

import com.example.demo.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<byte[]> generateReport(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        try {
            ByteArrayInputStream reportStream = reportService.generateXlsReport(start, end);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=report.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(reportStream.readAllBytes());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
