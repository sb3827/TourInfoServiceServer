package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.service.ReportService;
import com.yayum.tour_info_service_server.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
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

    //필터 조회
    @GetMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<List<ReportResponseDTO>>>reportFilter(@RequestParam String filter, @RequestParam String search){
        List<ReportResponseDTO> data=reportService.reportFilter(filter,search);
        ResponseDTO<List<ReportResponseDTO>> response=new ResponseDTO<>(true,data);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //신고 정보 조회
    @GetMapping(value = "/detail/{sno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<ReportDTO>> resportDetail(@PathVariable Long sno){
        ReportDTO data=reportService.reportDetail(sno);
        ResponseDTO<ReportDTO> response = new ResponseDTO<>(false, null);
        //신고가 존재하는 경우
        if(data!=null){
            response.setResult(true);
            response.setData(data);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //회원제재 내역 전체 조회
    @GetMapping(value = "/all/{mno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<List<DisciplinaryDTO>>> disciplinaryAll(@PathVariable Long mno){
        List<DisciplinaryDTO> data=reportService.disciplinaryUserData(mno);
        ResponseDTO<List<DisciplinaryDTO>> response=new ResponseDTO<>(true,data);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //신고
    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<Long>> report(@RequestBody ReportRequestDTO reportRequestDTO){
        ResponseDTO response=new ResponseDTO(false,null);
        Long data=reportService.report(reportRequestDTO);
        if(data==1l){
            response.setResult(true);
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        //만약 신고한 유저가 존재하지 않는다면 null을 전달받음

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //신고 확인(신고 상태 업데이트)
    @PutMapping(value = "/update/{sno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<Long>> reportIsDone(@PathVariable Long sno){
        Long data=reportService.reportUpdate(sno);
        ResponseDTO response=new ResponseDTO(false,null);
        if(data==-1l){
            response.setData(-1);
        }else if(data==sno){
            response.setResult(true);
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //제재
    @PostMapping(value="/disciplinary",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<Long>> disciplinary(@RequestBody DisciplinaryRequestDTO disciplinaryRequestDTO){
        ResponseDTO response=new ResponseDTO(false,null);

        // 유저 제재
        Long data=reportService.disciplinary(disciplinaryRequestDTO);

        // -1은 이미 정지된 유저
        // -2는 게시글과 댓글 둘다 신고가 된경우(게시글 신고 또는 댓글 신고만 가능)
        // -3은 신고가 존재하지 않는 경우
        if (data<=-1l){
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        else if(data>0){
            response.setResult(true);
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
