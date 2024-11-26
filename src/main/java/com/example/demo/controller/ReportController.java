package com.example.demo.controller;

import com.example.demo.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(ReportController.class);

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<byte[]> generateReport(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        logger.info("Received request to generate report from {} to {}", start, end);

        try {
            ByteArrayInputStream reportStream = reportService.generateXlsReport(start, end);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=report.xlsx");

            logger.info("Report generated successfully for range {} to {}", start, end);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(reportStream.readAllBytes());
        } catch (Exception e) {
            logger.error("Error occurred while generating report from {} to {}", start, end, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
