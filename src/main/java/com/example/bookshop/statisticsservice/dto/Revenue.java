package com.example.bookshop.statisticsservice.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Revenue {
    private Long soDon;
    private String tenSach;
    private BigDecimal tongTien;
    private Long soLuongBan;
    private Long donDaHuy;
}
