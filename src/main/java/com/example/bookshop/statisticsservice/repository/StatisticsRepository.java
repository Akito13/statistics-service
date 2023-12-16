package com.example.bookshop.statisticsservice.repository;

import com.example.bookshop.statisticsservice.dto.Revenue;
import com.example.bookshop.statisticsservice.model.Statistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
//    default List<Statistics> findAllByThoiGianDat(LocalDate date) {
//        return findAllByThoiGianDatBetween(date.atStartOfDay(), date.plusDays(1L).atStartOfDay());
//    }

    @Query("SELECT cast(COUNT(DISTINCT s.donhangId) as bigdecimal ) , cast(SUM(s.tongTien) as bigdecimal ) , cast(SUM(s.soLuong) as bigdecimal ) FROM Statistics s " +
            "WHERE s.thoiGianDat BETWEEN ?1 AND ?2 AND s.hoanTra = false")
    List<BigDecimal[]> findTotalRevenue(LocalDateTime start, LocalDateTime end);

    List<Statistics> findAllByDonhangIdIn(List<Long> longs);

    @Query("SELECT new com.example.bookshop.statisticsservice.dto.Revenue(" +
            "count(s.donhangId), " +
            "s.tenSach, " +
            "sum(case when s.hoanTra=false then s.tongTien else cast(0 as bigdecimal) end), " +
            "sum(case when s.hoanTra=false then s.soLuong else 0 end), " +
            "sum(case when s.hoanTra=true then 1L else 0L end))  " +
            "FROM Statistics s where s.thoiGianDat between ?1 and ?2 group by s.tenSach " +
            "order by sum(case when s.hoanTra=false then s.tongTien else cast(0 as bigdecimal) end) asc")
    Page<Revenue> findAllByDaySumAsc(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT new com.example.bookshop.statisticsservice.dto.Revenue(" +
            "count(s.donhangId), " +
            "s.tenSach, " +
            "sum(case when s.hoanTra=false then s.tongTien else cast(0 as bigdecimal) end), " +
            "sum(case when s.hoanTra=false then s.soLuong else 0L end), " +
            "sum(case when s.hoanTra=true then 1L else 0L end))  " +
            "FROM Statistics s where s.thoiGianDat between ?1 and ?2 group by s.tenSach " +
            "order by sum(case when s.hoanTra=false then s.tongTien else cast(0 as bigdecimal) end) desc")
    Page<Revenue> findAllByDaySumDesc(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT new com.example.bookshop.statisticsservice.dto.Revenue(" +
            "count(s.donhangId), " +
            "s.tenSach, " +
            "sum(case when s.hoanTra=false then s.tongTien else cast(0 as bigdecimal) end), " +
            "sum(case when s.hoanTra=false then s.soLuong else 0L end), " +
            "sum(case when s.hoanTra=true then 1L else 0L end))  " +
            "FROM Statistics s where s.thoiGianDat between ?1 and ?2 group by s.tenSach " +
            "order by sum(case when s.hoanTra=false then s.soLuong else 0L end) asc")
    Page<Revenue> findAllByDaySLAsc(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT new com.example.bookshop.statisticsservice.dto.Revenue(" +
            "count(s.donhangId), " +
            "s.tenSach, " +
            "sum(case when s.hoanTra=false then s.tongTien else cast(0 as bigdecimal) end), " +
            "sum(case when s.hoanTra=false then s.soLuong else 0L end), " +
            "sum(case when s.hoanTra=true then 1L else 0L end))  " +
            "FROM Statistics s where s.thoiGianDat between ?1 and ?2 group by s.tenSach " +
            "order by sum(case when s.hoanTra=false then s.soLuong else 0L end) desc")
    Page<Revenue> findAllByDaySLDesc(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT new com.example.bookshop.statisticsservice.dto.Revenue(" +
            "count(s.donhangId), " +
            "s.tenSach, " +
            "sum(case when s.hoanTra=false then s.tongTien else cast(0 as bigdecimal) end), " +
            "sum(case when s.hoanTra=false then s.soLuong else 0L end), " +
            "sum(case when s.hoanTra=true then 1L else 0L end))  " +
            "FROM Statistics s where s.thoiGianDat between ?1 and ?2 group by s.tenSach " +
            "order by s.tenSach asc")
    Page<Revenue> findAllByDayAsc(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT new com.example.bookshop.statisticsservice.dto.Revenue(" +
            "count(s.donhangId), " +
            "s.tenSach, " +
            "sum(case when s.hoanTra=false then s.tongTien else cast(0 as bigdecimal) end), " +
            "sum(case when s.hoanTra=false then s.soLuong else 0L end), " +
            "sum(case when s.hoanTra=true then 1L else 0L end))  " +
            "FROM Statistics s where s.thoiGianDat between ?1 and ?2 group by s.tenSach " +
            "order by s.tenSach desc")
    Page<Revenue> findAllByDayDesc(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
