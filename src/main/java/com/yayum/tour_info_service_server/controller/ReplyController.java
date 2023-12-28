package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.ReplyDTO;
import com.yayum.tour_info_service_server.dto.ReportDTO;
import com.yayum.tour_info_service_server.dto.ReportRequestDTO;
import com.yayum.tour_info_service_server.dto.ResponseWrapDTO;
import com.yayum.tour_info_service_server.security.util.SecurityUtil;
import com.yayum.tour_info_service_server.service.ReplyService;
import com.yayum.tour_info_service_server.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
  private final ReplyService replyService;
  private final ReportService reportService;

  @GetMapping("/mno/{mno}")
  public ResponseEntity<List<ReplyDTO>> getListByMno(@PathVariable("mno") Long mno) {
    log.info("getReplyListByMno....");
    List<ReplyDTO> result = replyService.getListOfReplyByMember(mno);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/bno/{bno}")
  public ResponseEntity<List<ReplyDTO>> getListByBno(@PathVariable("bno") Long bno) {
    log.info("getReplyListByBno....");
    List<ReplyDTO> result = replyService.getListOfReplyByBoard(bno);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO) {
    log.info("saveReply... " + replyDTO);
    replyService.saveReply(replyDTO);
    return new ResponseEntity<>(replyDTO.getRno(), HttpStatus.OK);
  }

  @PutMapping("/update/{rno}")
  public ResponseEntity<Long> update(@PathVariable Long rno, @RequestBody ReplyDTO replyDTO) {
    if (SecurityUtil.validateMno(replyDTO.getMno())) {
      log.info("updateReply...replyDTO : " + replyDTO);
      replyDTO.setRno(rno);
      replyService.modify(replyDTO);
      return new ResponseEntity<>(rno, HttpStatus.OK);
    } else {
      log.info("NO MNO match@@");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("/delete/{rno}")
  public ResponseEntity<Long> delete(@PathVariable Long rno, @RequestBody ReplyDTO replyDTO) {
    if (SecurityUtil.validateMno(replyDTO.getMno())) {
      log.info("deleteReply...replyDTO : " + replyDTO);
      replyDTO.setRno(rno);
      replyService.delete(replyDTO);
      return new ResponseEntity<>(rno, HttpStatus.OK);
    } else {
      log.info("NO MNO match@@");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(value = "/report",produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseWrapDTO<Long>> report(@RequestBody ReportRequestDTO reportRequestDTO){
    ResponseWrapDTO response=new ResponseWrapDTO(false,null);
    Long data=reportService.report(reportRequestDTO);
    if(data==1l){
      response.setResult(true);
      response.setData(data);
      return new ResponseEntity<>(response,HttpStatus.OK);
    }
    //만약 신고한 유저가 존재하지 않는다면 null을 전달받음

    return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
  }

}
