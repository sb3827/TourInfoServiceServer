package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.DisciplinaryDTO;
import com.yayum.tour_info_service_server.dto.ReportDTO;
import com.yayum.tour_info_service_server.dto.ReportFilterDTO;
import com.yayum.tour_info_service_server.repository.ReportRepository;
import com.yayum.tour_info_service_server.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    //전부 조회
    //필터 조회
    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>>reportFilter(@RequestParam ReportFilterDTO reportFilterDTO){
        return new ResponseEntity<>(reportService.reportFilter(reportFilterDTO),HttpStatus.OK);
    }

    //신고 정보 조회
    @GetMapping(value = "/detail",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> resportDetail(@RequestParam Long sno){
        return new ResponseEntity<>(reportService.reportDetail(sno),HttpStatus.OK);
    }

    //회원신고 내역 전체 조회
    @GetMapping(value = "/count",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Object>> disciplinaryAll(@RequestParam Long mno){
        return new ResponseEntity<>(reportService.disciplinaryUserData(mno),HttpStatus.OK);
    }

    //신고
    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> report(@RequestBody DisciplinaryDTO disciplinaryDTO){
        return new ResponseEntity<>(reportService.disciplinary(disciplinaryDTO),HttpStatus.OK);
    }

    //신고 상태 변경
    @PutMapping(value = "/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> reportIsDone(@RequestBody Long sno){
        return new ResponseEntity<>(reportService.reportUpdate(sno),HttpStatus.OK);
    }

    //제재
    @PostMapping(value="/disciplinary",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> discoplinary(@RequestBody DisciplinaryDTO disciplinaryDTO){
        return new ResponseEntity<>(reportService.disciplinary(disciplinaryDTO),HttpStatus.OK);
    }
}
