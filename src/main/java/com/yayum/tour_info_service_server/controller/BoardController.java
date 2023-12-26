package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.BoardDTO;
import com.yayum.tour_info_service_server.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
  private final BoardService boardService;

  // 장소 포스팅 등록
  @PostMapping(value = {"/place/posting/register"})
  public ResponseEntity<Long> registerPost (@RequestBody BoardDTO boardDTO) {
    Long bno = boardService.placeRegister(boardDTO);
    return  new ResponseEntity<>(bno, HttpStatus.OK);
  }

  // 장소,코스 포스팅, 댓글 삭제
  @DeleteMapping(value = {"/place/posting/delete/bno={bno}","/course/posting/delete/bno={bno}"})
  public ResponseEntity<Long> remove(@PathVariable("bno") Long bno) {
    log.info("delete...bno: "+bno);
    boardService.remove(bno);
    return new ResponseEntity<>(bno,HttpStatus.OK);
  }

  // 장소,코스 포스팅 수정
  @PutMapping(value = {"/place/posting/update","/course/posting/update"})
  public ResponseEntity<Long> modify(@RequestBody BoardDTO boardDTO){
   log.info("modify...dto: "+boardDTO);
   Long bno = boardService.modify(boardDTO);
   return new ResponseEntity<>(bno,HttpStatus.OK);
  }

//  // 장소 포스팅 정보 조회
//  @GetMapping(value = {"/place/posting/bno={bno}"})
//  public ResponseEntity<BoardDTO> getPlaceBoard(@PathVariable("bno") Long bno) {
//    log.info("getPlaceBoard... bno: "+ bno);
//    BoardDTO boardDTO = boardService.getPlaceBoardByBno(bno);
//    return new ResponseEntity<>(boardDTO, HttpStatus.OK);
//  }
//
//  // 코스 포스팅 정보 조회
//  @GetMapping(value = {"/course/posting/bno={bno}"})
//  public ResponseEntity<BoardDTO> getCourseBoard(@PathVariable("bno") Long bno) {
//    log.info("getCourseBoard... bno: "+ bno);
//    BoardDTO boardDTO = boardService.getCourseBoardByBno(bno);
//    return new ResponseEntity<>(boardDTO, HttpStatus.OK);
//  }

//  // 회원별 장소 포스팅 정보 조회
//  @GetMapping(value = {"/place/posting/mno={mno}"})
//  public ResponseEntity<BoardDTO> getBoardByMno(@PathVariable("mno") Long mno) {
//    log.info("getBoardByMno... bno: "+ mno);
//    BoardDTO boardDTO = boardService.getBoardByMno(mno);
//    return new ResponseEntity<>(boardDTO, HttpStatus.OK);
//  }
}
