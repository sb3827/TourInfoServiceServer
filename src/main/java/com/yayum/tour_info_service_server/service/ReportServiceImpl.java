package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.*;
import com.yayum.tour_info_service_server.repository.DisciplinaryRepository;
import com.yayum.tour_info_service_server.repository.MemberRepository;
import com.yayum.tour_info_service_server.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;

    private final MemberRepository memberRepository;

    private final DisciplinaryRepository disciplinaryRepository;

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
                    .bno(report.getBoard()!=null?report.getBoard().getBno():null)
                    .rno(report.getReply()!=null?report.getReply().getRno():null)
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
    public List<Object> disciplinaryUserData(Long mno) {
        List<Object> result=disciplinaryRepository.findAllByMember(Member.builder().mno(mno).build());
        return result;
    }

    //신고
    @Override
    public Long report(ReportRequestDTO reportRequestDTO) {
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
        return reportRequestDTO.getRno();
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
    public Long disciplinary(DisciplinaryRequestDTO disciplinaryRequestDTO) {
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
        return disciplinary.getDno();
    }
}
