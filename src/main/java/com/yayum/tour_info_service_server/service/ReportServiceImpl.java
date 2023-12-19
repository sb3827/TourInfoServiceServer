package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.*;
import com.yayum.tour_info_service_server.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;

    private final MemberRepository memberRepository;

    private final DisciplinaryRepository disciplinaryRepository;

    //게시글 및 댓글 삭제
    private final ReplyRepository replyRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardPlaceRepository boardPlaceRepository;
    private final ImageRepository imageRepository;
    private final BoardRepository boardRepository;

    //신고 내역 모두 조회

    //신고 필터 조회
    @Override
    public List<ReportResponseDTO> reportFilter(String filter,String search) {

        List<ReportResponseDTO> reportResponseDTOS=new ArrayList<>();
        List<Report> result;
        //전체 조회
        if(filter.equals("all")){
            result=reportRepository.searchReportAll(search);
        }
        //처리 중
        else if(filter.equals("reporting")){
            result=reportRepository.searchIsDone(false,search);
        }
        //처리 완료
        else if(filter.equals("reported")){
            result=reportRepository.searchIsDone(true,search);
        }
        //게시글 처리중
        else if(filter.equals("board_reporting")){
            result=reportRepository.searchBoardReport(false,search);
        }
        //게시글 처리완료
        else if(filter.equals("board_reported")){
            result=reportRepository.searchBoardReport(true,search);
        }
        //리뷰 처리중
        else if(filter.equals("reply_reporting")){
            result=reportRepository.searchReplyReport(false,search);
        }
        //리뷰 처리완료
        else if(filter.equals("reply_reported")){
            result=reportRepository.searchReplyReport(true,search);
        }else {
            return null;
        }
        //reportDTO로 형변환
        for (Report report:result){
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
        return reportResponseDTOS;
    }

    //신고 정보 조회
    @Override
    public ReportDTO reportDetail(Long sno) {
        Optional<Report> result=reportRepository.findById(sno);
        if (result.isPresent()){
            Report report=result.get();
            return entityToDto(report);
        }
        return null;
    }

    //유저에대한 정지 정보
    @Override
    public List<DisciplinaryDTO> disciplinaryUserData(Long mno) {
        //회원이 없을 경우 null 전달
        Optional<Member> member=memberRepository.findById(mno);
        if (!member.isPresent()){
            return null;
        }

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
        return disciplinaryDTOS;
    }

    //신고
    @Override
    public Long report(ReportRequestDTO reportRequestDTO) {
        //신고하고자하는 회원이 없을 경우 null 전달
        Optional<Member> member=memberRepository.findById(reportRequestDTO.getDefendant());
        if (!member.isPresent()){
            return null;
        }

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
        return 1l;
    }

    //신고 상태 업데이트
    @Override
    public Long reportUpdate(Long sno) {
        Optional<Report> result=reportRepository.findById(sno);
        if (result.isPresent()){
            Report report=result.get();
            report.changeIsDone(true);
            reportRepository.save(report);
            return report.getSno();
        }
        return -1l;
    }

    //제재 - merge후 board,reply delete 추가해야함
    @Override
    @Transactional
    public Long disciplinary(DisciplinaryRequestDTO disciplinaryRequestDTO) {
        List<Disciplinary> checkDisciplinary=disciplinaryRepository.findAllByMemberMnoOrderByExpDateDesc(disciplinaryRequestDTO.getMno());

        //이미 정지된 유저
        if(!checkDisciplinary.isEmpty() && checkDisciplinary.get(0).getExpDate().compareTo(LocalDateTime.now())>=0){
            return -1l;
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

        //해당 게시글 및 댓글 삭제 -> merge 후 겹치는부분 제거
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
                                .board(Board.builder().bno(r.getBoard().getBno()).build())
                                .text("신고 처리된 댓글 입니다.")
                                .build();
                        replyRepository.save(reply);
                }
            }
            //게시글, 댓글 둘다 있는경우 오류 처리
            else{
                return -2l;
            }
        }else{
            //신고가 존재하지 않는 경우
            return -3l;
        }
        //신고 상태 업데이트 -> 신고 처리 완료
        reportUpdate(disciplinaryRequestDTO.getSno());

        //제재
        disciplinaryRepository.save(disciplinary);
        return disciplinary.getDno();
    }
}
