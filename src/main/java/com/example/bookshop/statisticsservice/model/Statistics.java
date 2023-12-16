package com.example.bookshop.statisticsservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thongke")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thongkeId;
    private Long donhangId;
    private Long nguoiDungId;
    private Long dhctId;
    private String tenSach;
    private BigDecimal tongTien;
    private Integer soLuong;
    private Double phanTramGiam;
    private LocalDateTime thoiGianDat;
    private LocalDateTime thoiGianXuat;
    private String trangThai;
    private Boolean hoanTra;
}
