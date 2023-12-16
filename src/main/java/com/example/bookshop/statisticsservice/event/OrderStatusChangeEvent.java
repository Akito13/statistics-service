package com.example.bookshop.statisticsservice.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatusChangeEvent {
    private Long donhangId;
    private String trangThai;
    private Boolean hoanTra;
}