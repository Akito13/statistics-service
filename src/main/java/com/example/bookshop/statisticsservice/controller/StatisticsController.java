package com.example.bookshop.statisticsservice.controller;

import com.example.bookshop.statisticsservice.dto.ResponseDto;
import com.example.bookshop.statisticsservice.dto.ResponsePayload;
import com.example.bookshop.statisticsservice.dto.Revenue;
import com.example.bookshop.statisticsservice.model.Statistics;
import com.example.bookshop.statisticsservice.service.StatisticsService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/statistics")
public class StatisticsController {

    private StatisticsService service;

    @Autowired
    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @GetMapping("day/total")
    public ResponseEntity<List<BigDecimal[]>> getTotalDailyRevenue(@RequestParam("date") @JsonFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> d) {
        LocalDate date = d.orElse(LocalDate.now());
        var totalRevenues = service.getTotalRevenue(date);
        totalRevenues.forEach(System.out::println);
        return ResponseEntity.ok(totalRevenues);
    }

    @GetMapping("day")
    public ResponseEntity<ResponseDto<Revenue>> getStatsByDay(@RequestParam("date") @JsonFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> d,
                                                              @RequestParam("page") Optional<Integer> os,
//                                                              @RequestParam("tongTien") Optional<BigDecimal> g,
                                                              @RequestParam("direction") Optional<String> dir,
                                                              @RequestParam("sortBy") Optional<String> sb,
                                                              WebRequest request) {
        LocalDate date = d.orElse(LocalDate.now());
        int page = os.orElse(0);
        String direction = dir.orElse("asc");
        String sortBy = sb.orElse("tenSach");
        Page<Revenue> statisticsPage = null;
//        Sort sort = generateSortPolicy(sortBy, direction);
//        statisticsPage = service.getStatsByDayAndSach(date, page, 5, sort);
        if(sortBy.equals("tongTien")) {
            statisticsPage = service.getStatsByDayAndSum(date, page, 5, direction);
        } else if (sortBy.equals("soLuong")) {
            statisticsPage = service.getStatsByDayAndSL(date, page, 5, direction);
        } else {
            statisticsPage = service.getStatsByDayDefault(date, page, 5, direction);
        }
        ResponsePayload<Revenue> payload =  ResponsePayload.<Revenue>builder()
                .records(statisticsPage.getContent())
                .recordCounts(statisticsPage.getTotalElements())
                .currentPage(statisticsPage.getNumber())
                .currentPageSize(statisticsPage.getNumberOfElements())
                .totalPages(statisticsPage.getTotalPages())
                .pageSize(statisticsPage.getSize()).build();
        return generateResponseEntity(request, "OK", HttpStatus.OK, payload);
    }

//    private Sort generateSortPolicy(String sortBy, String directionString) {
//        directionString = directionString.equals("desc") ? "desc" : "asc";
//        Sort.Direction direction = Sort.Direction.fromString(directionString);
//        switch (sortBy) {
//            case "tongTien" -> Sort.by(direction, "tongTien");
//            default -> Sort.by(direction, "tenSach");
//        }
//    }

    public <T> ResponseEntity<ResponseDto<T>> generateResponseEntity(WebRequest request, String message, HttpStatus status, ResponsePayload<T> payload) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.<T>builder()
                        .apiPath(request.getDescription(false))
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .statusCode(status)
                        .payload(payload)
                        .build()
                );
    }
}
