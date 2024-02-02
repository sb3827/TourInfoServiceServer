package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Report;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    PageRequest pageRequest=PageRequest.of(0,10);

    //더미 삽입 테스트
    @Test
    public void testReportInsert(){
        Report report=Report.builder()
                .complainant_mno(2l)
                .defendant_mno(1l)
                .board_bno(1l)
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
        Page<Report> result=reportRepository.searchReportAll("",pageRequest);
        for (Report report:result){
            System.out.println(result);
        }
    }



    //신고 필터 - 처리 or 처리x
    @Transactional
    @Test
    public void testCheckReport(){
        Page<Report> result=reportRepository.searchIsDone(false,"",pageRequest);
        System.out.println(result);
    }

    //신고 필터 - 게시글
    @Transactional
    @Test
    public void testFilterBoardReport(){
        Page<Report> result=reportRepository.searchBoardReport(false,"t",pageRequest);
        System.out.println(result);
    }

    //신고 필터 - 댓글
    @Transactional
    @Test
    public void testFilterReplyReport(){
        Page<Report> result=reportRepository.searchReplyReport(false,"테",pageRequest);
        System.out.println(result);
    }

    //이미 신고한 내역인지 확인 - 게시글
    @Test
    public void testFindReport(){
        Report report=reportRepository.checkBoardReport(1l,3l);
        System.out.println(report==null);
    }


    //이미 신고한 내역인지 확인 - 댓글
    @Test
    public void testFindReplyReport(){
        System.out.println(reportRepository.checkReplyReport(36l,3l)==null);
    }

}