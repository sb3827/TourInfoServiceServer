package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.*;
import com.yayum.tour_info_service_server.repository.BoardRepository;
import com.yayum.tour_info_service_server.repository.DisciplinaryRepository;
import com.yayum.tour_info_service_server.repository.MemberRepository;
import com.yayum.tour_info_service_server.repository.ReportRepository;
import jakarta.transaction.Transactional;
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
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DisciplinaryRepository disciplinaryRepository;

    @Autowired
    private ReportService reportService;

    @Test
    public void testSearch(){
        String filter="all";
        String search="";

        List<ReportResponseDTO> reportResponseDTOS=new ArrayList<>();
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
//            ReportDTO reportDTO=reportService.entityToDto(report);
//            reportDTOS.add(reportDTO);
            Optional<Member> com=memberRepository.findById(report.getComplainant_mno());
            Optional<Member> def=memberRepository.findById(report.getDefendant_mno());

            ReportResponseDTO reportResponseDTO=ReportResponseDTO.builder()
                    .sno(report.getSno())
                    .complainant_mno(report.getComplainant_mno())
                    .complainant(com.get().getName())
                    .defendant_mno(report.getDefendant_mno())
                    .defendant(def.get().getName())
                    .bno(report.getBoard()!=null?report.getBoard().getBno():null)
                    .rno(report.getReply()!=null?report.getReply().getRno():null)
                    .content(report.getContent())
                    .isDone(report.getIsDone())
                    .message(report.getMessage())
                    .regDate(report.getRegDate())
                    .build();
            reportResponseDTOS.add(reportResponseDTO);
        }
        System.out.println(reportResponseDTOS);
    }

    //신고 정보 조회
    @Test
    public void reportDetail(){
        Long sno=1l;
        Optional<Report> result=reportRepository.findById(sno);
        if (result.isPresent()){
            Report report=result.get();
            System.out.println(reportService.entityToDto(report));
        }
    }

    //유저 제재내역 조회
    @Test
    @Transactional
    public void testUserDisciplinary(){
        Long mno=1l;
        List<Object> result=disciplinaryRepository.findAllByMember(Member.builder().mno(mno).build());
        System.out.println(result);
    }

    //신고
    @Test
    public void testReport(){
        ReportDTO reportDTO=ReportDTO.builder()
                .complainant(1l)
                .bno(2l)
                .defendant(2l)
                .message("testesttest")
                .content("<h2>zxcv</h2>")
                .isDone(false)
                .build();
        reportRepository.save(reportService.dtoToEntity(reportDTO));
        System.out.println(reportDTO);
    }

    //update 신고
    @Test
    public void testReportUpdate(){
        ReportRequestDTO reportRequestDTO=ReportRequestDTO.builder()
                .complainant(1l)
                .defendant(2l)
                .bno(1l)
                .content("test abcd")
                .message("zxcv")
                .build();

        Report report=Report.builder()
                .complainant_mno(reportRequestDTO.getComplainant())
                .defendant_mno(reportRequestDTO.getDefendant())
                .board(reportRequestDTO.getBno()!=null?Board.builder().bno(reportRequestDTO.getBno()).build():null)
                .reply(reportRequestDTO.getRno()!=null?Reply.builder().rno(reportRequestDTO.getRno()).build():null)
                .content(reportRequestDTO.getContent())
                .message(reportRequestDTO.getMessage())
                .isDone(false)
                .build();
        reportRepository.save(report);
    }


    //제재
    @Test
    public void disiplinaryTest(){
        DisciplinaryRequestDTO disciplinaryRequestDTO=DisciplinaryRequestDTO.builder()
                .mno(1l)
                .reason("test")
                .build();
        int row=disciplinaryRepository.findAllByMember(Member.builder().mno(disciplinaryRequestDTO.getMno()).build()).size();
        Disciplinary disciplinary;
        if(row>=4){
            disciplinary=Disciplinary.builder()
                    .member(Member.builder().mno(disciplinaryRequestDTO.getMno()).build())
                    .reason(disciplinaryRequestDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(null)
                    .build();
        }else if(row==3){
            disciplinary=Disciplinary.builder()
                    .member(Member.builder().mno(disciplinaryRequestDTO.getMno()).build())
                    .reason(disciplinaryRequestDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(30))
                    .build();
        }else if(row == 2){
            disciplinary=Disciplinary.builder()
                    .member(Member.builder().mno(disciplinaryRequestDTO.getMno()).build())
                    .reason(disciplinaryRequestDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(14))
                    .build();

        }else if(row==1){
            disciplinary=Disciplinary.builder()
                    .member(Member.builder().mno(disciplinaryRequestDTO.getMno()).build())
                    .reason(disciplinaryRequestDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(7))
                    .build();

        }else{
            disciplinary=Disciplinary.builder()
                    .member(Member.builder().mno(disciplinaryRequestDTO.getMno()).build())
                    .reason(disciplinaryRequestDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(3))
                    .build();
        }
        disciplinaryRepository.save(disciplinary);
        System.out.println(disciplinary.getDno());
    }

}