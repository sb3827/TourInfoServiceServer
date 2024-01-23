package com.dot.tour_info_service_server.controller;

import com.dot.tour_info_service_server.dto.request.reply.ReplyRequestDTO;
import com.dot.tour_info_service_server.dto.response.reply.ReplyListResponseDTO;
import com.dot.tour_info_service_server.dto.response.reply.ReplyMemberResponseDTO;
import com.dot.tour_info_service_server.security.util.SecurityUtil;
import com.dot.tour_info_service_server.service.reply.ReplyService;
import com.dot.tour_info_service_server.service.report.ReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reply")
@Log4j2
@RequiredArgsConstructor
@Validated
public class ReplyController {
  private final ReplyService replyService;
  private final ReportService reportService;

  // permit all
  @GetMapping("/member")
  public ResponseEntity<List<ReplyListResponseDTO>> getListByMno(@Valid @RequestParam("mno") @NotNull(message = "mno cannot be null") Long mno) {
    log.info("getReplyListByMno....");
    List<ReplyListResponseDTO> result = replyService.showReplyList(mno);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  //수정 시작 게시글 댓글 조회
  // permit all
  @GetMapping("/board")
  public ResponseEntity<List<ReplyMemberResponseDTO>> getListByBno(@Valid @RequestParam("bno") @NotNull(message = "bno cannot be null") Long bno, @RequestParam(value = "rno", required = false) Long rno) {
    log.info("getReplyListByBno....");
    List<ReplyMemberResponseDTO> result;
    //상위 댓글만 조회
    if (rno == null) {
      result = replyService.parentReply(bno);
    }
    //대댓글 조회
    else {
      result = replyService.childReply(bno, rno);
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  // authenticated
  @PostMapping("/register")
  public ResponseEntity<Map<String, Long>> register(@RequestBody @Valid ReplyRequestDTO replyRequestDTO) {
    log.info("saveReply... " + replyRequestDTO);
    Map<String, Long> result = new HashMap<>();
    result.put("bno", replyRequestDTO.getBno());

    try {
      replyService.saveReply(replyRequestDTO);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw e;
    }
  }

  // authenticated
  @PutMapping("/update")
  public ResponseEntity<Map<String, Long>> update(@RequestBody @Valid ReplyRequestDTO replyRequestDTO) {
    if (!SecurityUtil.validateMno(replyRequestDTO.getMno())) {
      log.error("mno not matched");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Map<String, Long> result = new HashMap<>();
    result.put("rno", replyRequestDTO.getRno());
    log.info("updateReply...replyDTO : " + replyRequestDTO);
    replyService.modify(replyRequestDTO);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  // authenticated
  @PutMapping("/delete")
  public ResponseEntity<Map<String, Long>> delete(@RequestBody @Valid ReplyRequestDTO replyRequestDTO) {
    if (!SecurityUtil.validateMno(replyRequestDTO.getMno())) {
      log.error("mno not matched");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Map<String, Long> result = new HashMap<>();
    result.put("rno", replyRequestDTO.getRno());
    log.info("deleteReply...replyDTO : " + replyRequestDTO);
    replyRequestDTO.setText("삭제된 댓글입니다");
    replyRequestDTO.setMno(null);
    replyService.modify(replyRequestDTO);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

//todo

// authenticated
//  @PostMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<ResponseWrapDTO<Long>> report(@RequestBody ReportRequestDTO reportRequestDTO) {
//    ResponseWrapDTO response = new ResponseWrapDTO(false, null);
//    Long data = reportService.report(reportRequestDTO);
//    if (data == 1l) {
//      response.setResult(true);
//      response.setData(data);
//      return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    //만약 신고한 유저가 존재하지 않는다면 null을 전달받음
//
//    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//  }

}
