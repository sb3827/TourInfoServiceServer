package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.*;

import java.util.List;

public interface ReportService {
    //신고 모두 조회
//    List<ReportDTO> reportAll();


    //신고 필터 조회
    List<ReportResponseDTO> reportFilter(String filter,String search);

    //해당 신고 내역 정보 조회
    ReportDTO reportDetail(Long sno);

    //해당 회원 제재조회
    List<Object> disciplinaryUserData(Long mno);

    //신고(게시글 or 댓글)
    Long report(ReportRequestDTO reportRequestDTO);

    //신고 상태 업데이트
    Long reportUpdate(Long sno);

    //제재
    Long disciplinary(DisciplinaryRequestDTO disciplinaryRequestDTO);
    default Report dtoToEntity(ReportDTO reportDTO){

        Report report=Report.builder()
                .sno(reportDTO.getSno())
                .complainant_mno(reportDTO.getComplainant())
                .defendant_mno(reportDTO.getDefendant())
                .board(reportDTO.getBno()!=null?Board.builder()
                        .bno(reportDTO.getBno())
                        .build():null)
                .reply(reportDTO.getRno()!=null?Reply.builder()
                        .rno(reportDTO.getRno())
                        .build():null)
                .content(reportDTO.getContent())
                .isDone(reportDTO.getIsDone())
                .message(reportDTO.getMessage())
                .build();
        return report;
    }

    default ReportDTO entityToDto(Report report){
        ReportDTO reportDTO=ReportDTO.builder()
                .sno(report.getSno())
                .complainant(report.getComplainant_mno())
                .defendant(report.getDefendant_mno())
                .bno(report.getBoard()!=null?report.getBoard().getBno():null)
                .rno(report.getReply()!=null?report.getReply().getRno():null)
                .content(report.getContent())
                .isDone(report.getIsDone())
                .message(report.getMessage())
                .regDate(report.getRegDate())
                .build();
        return reportDTO;
    }



}
