package com.example.bookshop.statisticsservice.service;

import com.example.bookshop.statisticsservice.dto.Revenue;
import com.example.bookshop.statisticsservice.model.Statistics;
import com.example.bookshop.statisticsservice.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticsService {

    private StatisticsRepository repository;

    @Autowired
    public StatisticsService(StatisticsRepository repository) {
        this.repository = repository;
    }

    public List<BigDecimal[]> getTotalRevenue(LocalDate date) {
        return repository.findTotalRevenue(date.atStartOfDay(), date.plusDays(1L).atStartOfDay());
    }

    public Page<Revenue> getStatsByDayAndSum(LocalDate date, int page, int pageSize, String direction) {
        if(direction.equals("desc")) {
            return repository.findAllByDaySumDesc(date.atStartOfDay(), date.plusDays(1L).atStartOfDay(), PageRequest.of(page, pageSize));
        }
        return repository.findAllByDaySumAsc(date.atStartOfDay(), date.plusDays(1L).atStartOfDay(), PageRequest.of(page, pageSize));
    }

    public Page<Revenue> getStatsByDayAndSL(LocalDate date, int page, int pageSize, String direction) {
        if(direction.equals("desc")) {
            return repository.findAllByDaySLDesc(date.atStartOfDay(), date.plusDays(1L).atStartOfDay(), PageRequest.of(page, pageSize));
        }
        return repository.findAllByDaySLAsc(date.atStartOfDay(), date.plusDays(1L).atStartOfDay(), PageRequest.of(page, pageSize));
    }

    public Page<Revenue> getStatsByDayDefault(LocalDate date, int page, int pageSize, String direction) {
        System.out.println("DEFAULT");
        if(direction.equals("desc")) {
            return repository.findAllByDayDesc(date.atStartOfDay(), date.plusDays(1L).atStartOfDay(), PageRequest.of(page, pageSize));
        }
        return repository.findAllByDayAsc(date.atStartOfDay(), date.plusDays(1L).atStartOfDay(), PageRequest.of(page, pageSize));
    }
}
