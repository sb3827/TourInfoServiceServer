package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Report;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Modifying
    @Transactional
    @Query("update Report r set r.board.bno = null")
    void updateReportBnoNull(List<Long> bno);

}
