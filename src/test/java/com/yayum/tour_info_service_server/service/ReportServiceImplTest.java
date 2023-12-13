package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.DisciplinaryDTO;
import com.yayum.tour_info_service_server.dto.ReportDTO;
import com.yayum.tour_info_service_server.entity.Disciplinary;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Report;
import com.yayum.tour_info_service_server.repository.DisciplinaryRepository;
import com.yayum.tour_info_service_server.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReportServiceImplTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private DisciplinaryRepository disciplinaryRepository;

    @Autowired
    private ReportService reportService;

    @Test
    public void testSearch(){
        String filter="all";
        String search="";

        List<ReportDTO> reportDTOS=new ArrayList<>();
        List<Report> result;
        //전체 조회
        if(filter=="all"){
            result=reportRepository.searchReportAll(search);
        }
        //처리 중
        else if(filter=="reporting"){
            result=reportRepository.searchIsDone(false,search);
        }
        //처리 완료
        else if(filter=="reported"){
            result=reportRepository.searchIsDone(true,search);
        }
        //게시글 처리중
        else if(filter=="board_reporting"){
            result=reportRepository.searchBoardReport(false,search);
        }
        //게시글 처리완료
        else if(filter=="board_reported"){
            result=reportRepository.searchBoardReport(true,search);
        }
        //리뷰 처리중
        else if(filter=="reply_reporting"){
            result=reportRepository.searchReplyReport(false,search);
        }
        //리뷰 처리완료
        else if(filter=="reply_reported"){
            result=reportRepository.searchReplyReport(true,search);
        }else {
            return ;
        }
        //reportDTO로 형변환
        for (Report report:result){
            ReportDTO reportDTO=reportService.entityToDto(report);
            reportDTOS.add(reportDTO);
        }
        System.out.println(reportDTOS);
    }

    //신고 정보 조회
    @Test
    public void reportDetail(){
        Long sno=2l;
        Optional<Report> result=reportRepository.findById(sno);
        if (result.isPresent()){
            Report report=result.get();
            System.out.println(reportService.entityToDto(report));
        }
    }

    //유저 제재내역 조회
    @Test
    public void testUserDisciplinary(){
        Long mno=2l;
        List<Object> result=disciplinaryRepository.findAllByMember(Member.builder().mno(mno).build());
        System.out.println(result.size());
    }

    //신고
    @Test
    public void testReport(){
        ReportDTO reportDTO=ReportDTO.builder()
                .sno(4l)
                .complainant(2l)
                .bno(1l)
                .defendant(1l)
                .message("testest")
                .build();
        reportRepository.save(reportService.dtoToEntity(reportDTO));
        System.out.println(reportDTO);
    }

    //update 신고
    @Test
    public void testReportUpdate(){
        Long sno=2l;
        Optional<Report> result=reportRepository.findById(sno);
        if (result.isPresent()){
            Report report=result.get();
            report.changeIsDone(true);
            reportRepository.save(report);
        }
    }


    //제재
    @Test
    public void disiplinaryTest(){
        DisciplinaryDTO disciplinaryDTO=DisciplinaryDTO.builder()
                .dno(2l)
                .mno(1l)
                .reason("테스트")
                .build();
        int row=disciplinaryRepository.findAllByMember(Member.builder().mno(disciplinaryDTO.getMno()).build()).size();
        Disciplinary disciplinary;
        if(row>=4){
            disciplinary=Disciplinary.builder()
                    .dno(disciplinaryDTO.getDno())
                    .member(Member.builder().mno(disciplinaryDTO.getMno()).build())
                    .reason(disciplinaryDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(null)
                    .build();
        }else if(row==3){
            disciplinary=Disciplinary.builder()
                    .dno(disciplinaryDTO.getDno())
                    .member(Member.builder().mno(disciplinaryDTO.getMno()).build())
                    .reason(disciplinaryDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(30))
                    .build();
        }else if(row == 2){
            disciplinary=Disciplinary.builder()
                    .dno(disciplinaryDTO.getDno())
                    .member(Member.builder().mno(disciplinaryDTO.getMno()).build())
                    .reason(disciplinaryDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(14))
                    .build();

        }else if(row==1){
            disciplinary=Disciplinary.builder()
                    .dno(disciplinaryDTO.getDno())
                    .member(Member.builder().mno(disciplinaryDTO.getMno()).build())
                    .reason(disciplinaryDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(7))
                    .build();

        }else{
            disciplinary=Disciplinary.builder()
                    .dno(disciplinaryDTO.getDno())
                    .member(Member.builder().mno(disciplinaryDTO.getMno()).build())
                    .reason(disciplinaryDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(3))
                    .build();
        }
        disciplinaryRepository.save(disciplinary);
        System.out.println(disciplinary.getDno());
    }

}