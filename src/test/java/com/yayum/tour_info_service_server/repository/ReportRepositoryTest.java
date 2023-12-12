package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    public void testInsert(){
        Report report=Report.builder()
                .complainant(Member.builder().mno(2l).build())
                .board(Board.builder().bno(2l).build())
                .isDone(false)
                .message("테스트~")
                .build();
        reportRepository.save(report);
    }

    //신고 전부 조회
    @Test
    public void testAllReport(){
        List<Report> result=reportRepository.findAllByOrderByRegDateDesc();
        System.out.println(result.size());
    }

    //신고 필터 - 처리 or 처리x
    @Test
    public void testCheckReport(){
        List<Report> result=reportRepository.searchIsDone(false,"");
        System.out.println(result.size());
    }

    //신고 필터 - 게시글
    @Test
    public void testFilterBoardReport(){
        List<Report> result=reportRepository.searchBoardReport(false,"테");
        System.out.println(result.size());
    }

    //신고 필터 - 댓글
    @Test
    public void testFilterReplyReport(){
        List<Report> result=reportRepository.searchReplyReport(false,"테");
        System.out.println(result.size());
    }

}