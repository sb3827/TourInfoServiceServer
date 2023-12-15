package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.service.ReportService;
import com.yayum.tour_info_service_server.util.ResponseWrapper;
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
    public ResponseEntity<ResponseWrapper<List<ReportResponseDTO>>>reportFilter(@RequestParam String filter,@RequestParam String search){
        List<ReportResponseDTO> data=reportService.reportFilter(filter,search);
        ResponseWrapper<List<ReportResponseDTO>> response=new ResponseWrapper<>(true,data);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //신고 정보 조회
    @GetMapping(value = "/detail/{sno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<ReportDTO>> resportDetail(@PathVariable Long sno){
        ReportDTO data=reportService.reportDetail(sno);
        ResponseWrapper<ReportDTO> response = new ResponseWrapper<>(false, null);
        //신고가 존재하는 경우
        if(data!=null){
            response.setResult(true);
            response.setData(data);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //회원제재 내역 전체 조회
    @GetMapping(value = "/all/{mno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<List<DisciplinaryDTO>>> disciplinaryAll(@PathVariable Long mno){
        List<DisciplinaryDTO> data=reportService.disciplinaryUserData(mno);
        ResponseWrapper<List<DisciplinaryDTO>> response=new ResponseWrapper<>(true,data);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //신고
    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<Long>> report(@RequestBody ReportRequestDTO reportRequestDTO){
        ResponseWrapper response=new ResponseWrapper(false,null);
        Long data=reportService.report(reportRequestDTO);
        if(data==1l){
            response.setResult(true);
            response.setData(data);
        }
        //만약 신고한 유저가 존재하지 않는다면 null을 전달받음

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //신고 확인(신고 상태 업데이트)
    @PutMapping(value = "/update/{sno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<Long>> reportIsDone(@PathVariable Long sno){
        Long data=reportService.reportUpdate(sno);
        ResponseWrapper response=new ResponseWrapper(false,null);
        if(data==-1l){
            response.setData(-1);
        }else if(data==sno){
            response.setResult(true);
            response.setData(data);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //제재
    @PostMapping(value="/disciplinary",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper<Long>> disciplinary(@RequestBody DisciplinaryRequestDTO disciplinaryRequestDTO){
        ResponseWrapper response=new ResponseWrapper(false,null);
        //신고 완료 처리
        reportService.reportUpdate(disciplinaryRequestDTO.getSno());
        //유저 제재
        Long data=reportService.disciplinary(disciplinaryRequestDTO);
        //이미 정지된 유저
        if (data==-1l){
            response.setResult(false);
            response.setData(-1);
        }else if(data>0){
            response.setResult(true);
            response.setData(data);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
