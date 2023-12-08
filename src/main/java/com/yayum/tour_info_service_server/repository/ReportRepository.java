package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {
    //신고 전체 조회 - 최신순
    List<Report> findAllByOrderByRegDateDesc();


}
