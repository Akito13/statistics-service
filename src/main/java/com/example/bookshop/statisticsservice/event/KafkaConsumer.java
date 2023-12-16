package com.example.bookshop.statisticsservice.event;

import com.example.bookshop.statisticsservice.model.Statistics;
import com.example.bookshop.statisticsservice.repository.StatisticsRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumer {
    private StatisticsRepository repository;

    @Autowired
    public KafkaConsumer(StatisticsRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "${constant.kafka.order-placed}",
            groupId = "${spring.kafka.consumer.group-id}",
            properties = {"value.deserializer:com.example.bookshop.statisticsservice.event.OrderPlacementValueDeserializer"}
    )
    public void consumeOrderPlacementEvent(ConsumerRecord<String, List<OrderPlacementEvent>> record) {
        List<OrderPlacementEvent> stats = record.value();
        List<Statistics> statistics = stats.stream().map(ope -> Statistics.builder()
                .dhctId(ope.getDhctId())
                .donhangId(ope.getDonhangId())
                .nguoiDungId(ope.getNguoiDungId())
                .phanTramGiam(ope.getPhanTramGiam())
                .tongTien(ope.getTongTien())
                .thoiGianDat(ope.getThoiGianDat())
                .thoiGianXuat(ope.getThoiGianXuat())
                .trangThai(ope.getTrangThai())
                .tenSach(ope.getTenSach())
                .soLuong(ope.getSoLuong())
                .hoanTra(ope.getHoanTra())
                .build()).peek(statistics1 -> System.out.println(statistics1.getTrangThai())).toList();
        repository.saveAll(statistics);
        System.out.println("Lưu vào thống kê thành công");
    }

    @KafkaListener(
            topics = "${constant.kafka.order-status-changed}",
            groupId = "${spring.kafka.consumer.group-id}",
            properties = {"value.deserializer:com.example.bookshop.statisticsservice.event.OrderStatusChangeValueDeserializer"}
    )
    public void consumeOrderStatusChangeEvent(ConsumerRecord<String, List<OrderStatusChangeEvent>> record) {
        List<OrderStatusChangeEvent> data = record.value();
        List<Statistics> stats = repository.findAllByDonhangIdIn(data.stream().map(OrderStatusChangeEvent::getDonhangId).toList());
        if(!stats.isEmpty()) {
            OrderStatusChangeEvent singleData = data.get(0);
            stats.forEach(statistics -> {
                statistics.setTrangThai(singleData.getTrangThai());
                statistics.setHoanTra(singleData.getHoanTra());
            });
            repository.saveAll(stats);
        }
    }
}
