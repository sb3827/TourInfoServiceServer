package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    //필터 조회
    @GetMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReportResponseDTO>>reportFilter(@RequestParam String filter,@RequestParam String search){
        List<ReportResponseDTO> result=reportService.reportFilter(filter,search);
        System.out.println(result);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //신고 정보 조회
    @GetMapping(value = "/detail/{sno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> resportDetail(@PathVariable Long sno){
        return new ResponseEntity<>(reportService.reportDetail(sno),HttpStatus.OK);
    }

    //회원제재 내역 전체 조회
    @GetMapping(value = "/all/{mno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DisciplinaryDTO>> disciplinaryAll(@PathVariable Long mno){
        return new ResponseEntity<>(reportService.disciplinaryUserData(mno),HttpStatus.OK);
    }

    //신고
    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> report(@RequestBody ReportRequestDTO reportRequestDTO){
        return new ResponseEntity<>(reportService.report(reportRequestDTO),HttpStatus.OK);
    }

    //신고 확인
    @PutMapping(value = "/update/{sno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> reportIsDone(@PathVariable Long sno){
        System.out.println(sno);
        return new ResponseEntity<>(reportService.reportUpdate(sno),HttpStatus.OK);
    }

    //제재
    @PostMapping(value="/disciplinary",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> disciplinary(@RequestBody DisciplinaryRequestDTO disciplinaryRequestDTO){
        reportService.reportUpdate(disciplinaryRequestDTO.getSno());
        return new ResponseEntity<>(reportService.disciplinary(disciplinaryRequestDTO),HttpStatus.OK);
    }
}
