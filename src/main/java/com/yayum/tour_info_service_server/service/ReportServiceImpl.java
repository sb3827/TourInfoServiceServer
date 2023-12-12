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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;

    private final DisciplinaryRepository disciplinaryRepository;

    //신고 내역 모두 조회
    @Override
    public List<ReportDTO> reportAll() {
        List<Report> result=reportRepository.findAllByOrderByRegDateDesc();
        return result.stream().map(report -> entityToDto(report)).collect(Collectors.toList());
    }

    //신고 필터 조회
    @Override
    public List<ReportDTO> reportFilter(ReportFilterDTO reportFilterDTO) {
        List<Report> result=reportRepository.searchBoardReport(reportFilterDTO.getFilter(),reportFilterDTO.getSearch());
        System.out.println(result);
        return null;
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

    //제재
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
        }else if(row>=3){
            disciplinary=Disciplinary.builder()
                    .dno(disciplinaryDTO.getDno())
                    .member(Member.builder().mno(disciplinaryDTO.getMno()).build())
                    .reason(disciplinaryDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(30))
                    .build();
        }else if(row>=2){
            disciplinary=Disciplinary.builder()
                    .dno(disciplinaryDTO.getDno())
                    .member(Member.builder().mno(disciplinaryDTO.getMno()).build())
                    .reason(disciplinaryDTO.getReason())
                    .strDate(LocalDateTime.now())
                    .expDate(LocalDateTime.now().plusDays(14))
                    .build();

        }else if(row>=1){
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
