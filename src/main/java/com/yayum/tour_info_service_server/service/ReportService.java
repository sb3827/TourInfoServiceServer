package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.DisciplinaryDTO;
import com.yayum.tour_info_service_server.dto.ReportDTO;
import com.yayum.tour_info_service_server.dto.ReportFilterDTO;
import com.yayum.tour_info_service_server.entity.*;

import java.util.List;

public interface ReportService {
    //신고 모두 조회
//    List<ReportDTO> reportAll();


    //신고 필터 조회
    List<Object> reportFilter(ReportFilterDTO reportFilterDTO);

    //해당 신고 내역 정보 조회
    ReportDTO reportDetail(Long sno);

    //해당 회원 제재조회
    List<Object> disciplinaryUserData(Long mno);

    //신고(게시글 or 댓글)
    Long report(ReportDTO reportDTO);

    //신고 상태 업데이트
    Long reportUpdate(Long sno);

    //제재
    Long disciplinary(DisciplinaryDTO disciplinaryDTO);
    default Report dtoToEntity(ReportDTO reportDTO){
        Report report=Report.builder()
                .sno(reportDTO.getSno())
                .complainant(Member.builder()
                        .mno(reportDTO.getMno())
                        .build())
                .board(Board.builder()
                        .bno(reportDTO.getBno())
                        .build())
                .reply(Reply.builder()
                        .rno(reportDTO.getRno())
                        .build())
                .isDone(reportDTO.getIsDone())
                .message(reportDTO.getMessage())
                .build();
        return report;
    }

    default ReportDTO entityToDto(Report report){
        ReportDTO reportDTO=ReportDTO.builder()
                .sno(report.getSno())
                .mno(report.getComplainant().getMno())
                .bno(report.getBoard().getBno())
                .rno(report.getReply().getRno())
                .isDone(report.getIsDone())
                .message(report.getMessage())
                .regDate(report.getRegDate())
                .build();
        return reportDTO;
    }



}
