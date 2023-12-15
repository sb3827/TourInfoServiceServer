package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.dto.ReportDTO;
import com.yayum.tour_info_service_server.entity.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    public void testReportInsert(){
        Report report=Report.builder()
                .complainant_mno(2l)
                .defendant_mno(1l)
                .board(Board.builder().bno(1l).build())
                .content("<h1>테스트<h1>")
                .isDone(false)
                .message("테스트~")
                .build();
        reportRepository.save(report);
    }

    //신고 전부 조회
    @Transactional
    @Test
    public void testAllReport(){
        List<Report> result=reportRepository.searchReportAll("");
        System.out.println(result);
    }



    //신고 필터 - 처리 or 처리x
    @Transactional
    @Test
    public void testCheckReport(){
        List<Report> result=reportRepository.searchIsDone(false,"");
        System.out.println(result);
    }

    //신고 필터 - 게시글
    @Transactional
    @Test
    public void testFilterBoardReport(){
        List<Report> result=reportRepository.searchBoardReport(false,"t");
        System.out.println(result);
    }

    //신고 필터 - 댓글
    @Transactional
    @Test
    public void testFilterReplyReport(){
        List<Report> result=reportRepository.searchReplyReport(false,"테");
        System.out.println(result);
    }

}