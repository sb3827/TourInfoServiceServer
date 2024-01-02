package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.entity.*;
import com.dot.tour_info_service_server.repository.*;
import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.*;
import com.yayum.tour_info_service_server.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ReportServiceImplTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DisciplinaryRepository disciplinaryRepository;

    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private BoardLikeRepository boardLikeRepository;
    @Autowired
    private BoardPlaceRepository boardPlaceRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private BoardRepository boardRepository;

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
                    .bno(report.getBoard_bno()!=null?report.getBoard_bno():null)
                    .rno(report.getReply_rno()!=null?report.getReply_rno():null)
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
        List<Disciplinary> result=disciplinaryRepository.findAllByMemberMnoOrderByExpDateDesc(mno);
        List<DisciplinaryDTO> disciplinaryDTOS=new ArrayList<>();
        for(Disciplinary disciplinary:result){
            DisciplinaryDTO disciplinaryDTO=DisciplinaryDTO.builder()
                    .dno(disciplinary.getDno())
                    .mno(disciplinary.getMember().getMno())
                    .reason(disciplinary.getReason())
                    .strDate(disciplinary.getStrDate())
                    .expDate(disciplinary.getExpDate())
                    .build();
            disciplinaryDTOS.add(disciplinaryDTO);
        }
        System.out.println(disciplinaryDTOS);
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
                .complainant(2l)
                .defendant(1l)
                .bno(2l)
                .rno(null)
                .content("test abcd")
                .message("zxcv")
                .build();

        Report report=Report.builder()
                .complainant_mno(reportRequestDTO.getComplainant())
                .defendant_mno(reportRequestDTO.getDefendant())
                .board_bno(reportRequestDTO.getBno()!=null?reportRequestDTO.getBno():null)
                .reply_rno(reportRequestDTO.getRno()!=null?reportRequestDTO.getRno():null)
                .content(reportRequestDTO.getContent())
                .message(reportRequestDTO.getMessage())
                .isDone(false)
                .build();
        reportRepository.save(report);
    }


    //제재
    @Test
    @Transactional
    @Rollback(false)
    public void disiplinaryTest(){
        DisciplinaryRequestDTO disciplinaryRequestDTO=DisciplinaryRequestDTO.builder()
                .sno(7l)
                .mno(1l)
                .reason("test")
                .build();

        List<Disciplinary> checkDisciplinary=disciplinaryRepository.findAllByMemberMnoOrderByExpDateDesc(disciplinaryRequestDTO.getMno());

        //이미 정지된 유저
        if(!checkDisciplinary.isEmpty() && checkDisciplinary.get(0).getExpDate().compareTo(LocalDateTime.now())>=0){
            System.out.println("-1 에러");
            return;
        }

        int row=checkDisciplinary.size();

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

        //해당 게시글 및 댓글 삭제
        Optional<Report> report=reportRepository.findById(disciplinaryRequestDTO.getSno());

        if(report.isPresent()){
            Report result=report.get();

            if (result.getBoard_bno()!=null){ // 게시글 삭제

                //댓글 삭제
                replyRepository.deleteAllByBoard(Board.builder().bno(result.getBoard_bno()).build());

                //좋아요 삭제
                boardLikeRepository.deleteAllByBoardLikePKBoard(Board.builder().bno(result.getBoard_bno()).build());

                //이미지 삭제
                imageRepository.deleteAllByBoard(Board.builder().bno(result.getBoard_bno()).build());

                //BoardPlace 삭제
                boardPlaceRepository.deleteAllByBoardPlacePKBoard(Board.builder().bno(result.getBoard_bno()).build());

                //Board 삭제
                boardRepository.deleteById(result.getBoard_bno());

            }else if(result.getReply_rno()!=null){ //댓글 처리
                //대댓글이 없는 경우
                if (replyRepository.countAllByParent(Reply.builder().rno(result.getReply_rno()).build())==0){
                    replyRepository.deleteById(result.getReply_rno());
                }
                //대댓글이거나 대댓글이 존재하는 경우
                else{
                    Optional<Reply> checkReply=replyRepository.findById(result.getReply_rno());
                    Reply r=checkReply.get();

                    Reply reply = Reply.builder()
                            .rno(result.getReply_rno())
                            .parent(r.getParent()!=null?r.getParent():null)
                            .member(null)
                            .board(Board.builder().bno(result.getBoard_bno()).build())
                            .text("삭제된 댓글 입니다.")
                            .build();
                    replyRepository.save(reply);
                }
            }
            //게시글, 댓글 둘다 있는경우 오류 처리
            else{
                System.out.println("-2 에러 - 게시글,댓글 둘다 있거나 없는 경우");
                return;
            }
        }else{
            System.out.println(-3+" 에러 - report 존재 x");
            return;
        }
        disciplinaryRepository.save(disciplinary);
    }

}