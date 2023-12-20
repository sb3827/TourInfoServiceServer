package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.ReplyDTO;
import com.yayum.tour_info_service_server.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
  private final ReplyService replyService;

  @GetMapping("/{mno}")
  public ResponseEntity<List<ReplyDTO>> getListByMno(@PathVariable("mno") Long mno) {
    log.info("getReplyListByMno....");
    List<ReplyDTO> result = replyService.getListOfReplyByMember(mno);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/{bno}")
  public ResponseEntity<List<ReplyDTO>> getListByBno(@PathVariable("bno") Long bno) {
    log.info("getReplyListByBno....");
    List<ReplyDTO> result = replyService.getListOfReplyByBoard(bno);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody ReplyDTO replyDTO) {
    replyService.saveReply(replyDTO);
    return new ResponseEntity<>("successfully registered", HttpStatus.OK);
  }

  @PutMapping("/update/{rno}")
  public ResponseEntity<String> update(@PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO) {
    replyService.modify(replyDTO);
    return new ResponseEntity<>("successfully updated", HttpStatus.OK);
  }

  @PutMapping("/delete/{rno}")
  public ResponseEntity<String> delete(@PathVariable("rno") Long rno, @RequestBody ReplyDTO replyDTO) {
    replyService.delete(replyDTO);
    return new ResponseEntity<>("successfully deleted", HttpStatus.OK);
  }
}
