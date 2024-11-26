package com.example.demo.service;

import com.example.demo.models.Report;
import com.example.demo.repository.ReportRepository;
import com.example.demo.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final Logger logger = LogManager.getLogger(ReportService.class);

    private final ReportRepository reportRepository;
    private final ReservationRepository reservationRepository;

    public Report generateReport(LocalDateTime start, LocalDateTime end) {
        logger.info("Generating report for reservations between {} and {}", start, end);
        int reservationCount = reservationRepository.countByReservationTimeBetween(start, end);

        Report report = new Report();
        report.setGeneratedAt(LocalDateTime.now());
        report.setReservationCount(reservationCount);

        logger.info("Report generated with {} reservations", reservationCount);
        return reportRepository.save(report);
    }

    public ByteArrayInputStream generateXlsReport(LocalDateTime start, LocalDateTime end) throws IOException {
        logger.info("Generating XLS report for reservations between {} and {}", start, end);
        int reservationCount = reservationRepository.countByReservationTimeBetween(start, end);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        String[] columns = {"Data rozpoczęcia", "Data zakończenia", "Wygenerowano", "Liczba rezerwacji"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue(start.toString());
        row.createCell(1).setCellValue(end.toString());
        row.createCell(2).setCellValue(LocalDateTime.now().toString());
        row.createCell(3).setCellValue(reservationCount);

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        logger.info("XLS report generated with {} reservations", reservationCount);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
