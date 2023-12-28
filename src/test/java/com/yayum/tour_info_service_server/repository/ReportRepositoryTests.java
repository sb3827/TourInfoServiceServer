package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportRepositoryTests {

  @Autowired
  private ReportRepository reportRepository;

  @Test
  public void testReportInsert(){
    Report report=Report.builder()
        .complainant_mno(2l)
        .defendant_mno(11l)
        .content("<h1>테스트<h1>")
        .isDone(false)
        .message("테스트~")
        .build();
    reportRepository.save(report);
  }


}