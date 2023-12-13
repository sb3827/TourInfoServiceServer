package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.DisciplinaryDTO;
import com.yayum.tour_info_service_server.dto.ReportDTO;
import com.yayum.tour_info_service_server.dto.ReportFilterDTO;
import com.yayum.tour_info_service_server.entity.Disciplinary;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Report;
import com.yayum.tour_info_service_server.repository.DisciplinaryRepository;
import com.yayum.tour_info_service_server.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;

    private final DisciplinaryRepository disciplinaryRepository;

    //신고 내역 모두 조회

    //신고 필터 조회
    @Override
    public List<ReportDTO> reportFilter(ReportFilterDTO reportFilterDTO) {
        String filter=reportFilterDTO.getFilter();
        String search=reportFilterDTO.getSearch();

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
            return null;
        }
        //reportDTO로 형변환
        for (Report report:result){
            ReportDTO reportDTO=entityToDto(report);
            reportDTOS.add(reportDTO);
        }
        return reportDTOS;
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
    public Long report(ReportDTO reportDTO) {
        reportRepository.save(dtoToEntity(reportDTO));
        return reportDTO.getRno();
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

    //제재 - merge후 board delete 추가해야함
    @Override
    public Long disciplinary(DisciplinaryDTO disciplinaryDTO) {
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
        return disciplinaryDTO.getDno();
    }
}
