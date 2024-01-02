package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.security.util.SecurityUtil;
import com.yayum.tour_info_service_server.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    // 장소 포스팅 등록
    @PostMapping(value = {"/place/posting/register"})
    public ResponseEntity<Map<String, Long>> placeRegisterPost(@RequestBody PlaceBoardDTO placeBoardDTO) {
        Map<String, Long> result = new HashMap<>();
        Long bno = boardService.placeRegister(placeBoardDTO);
        result.put("bno", bno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 코스포스팅 등록
    @PostMapping(value = {"/course/posting/register"})
    public ResponseEntity<Map<String, Long>> courseRegisterPost(@RequestBody CourseBoardDTO courseBoardDTO) {
        Map<String, Long> result = new HashMap<>();
        Long bno = boardService.courseRegister(courseBoardDTO);
        result.put("bno", bno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 장소,코스 포스팅 삭제
    @DeleteMapping(value = {"/place/posting/delete", "/course/posting/delete"})
    public ResponseEntity<Map<String, Long>> remove(@RequestParam("bno") Long bno) {
        Map<String, Long> result = new HashMap<>();
        boardService.remove(bno);
        result.put("bno", bno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 장소 포스팅 수정
    @PutMapping(value = {"/place/posting/update"})
    public ResponseEntity<Map<String, Long>> modifyPlace(@RequestBody PlaceBoardDTO placeBoardDTO) {
        log.info("modify...dto: " + placeBoardDTO);
        Map<String, Long> result = new HashMap<>();
        if (!SecurityUtil.validateMno(placeBoardDTO.getWriter())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Long bno = boardService.placeBoardModify(placeBoardDTO);
            result.put("bno", bno);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 코스 게시글 수정 address
    @PutMapping("/course/posting/update")
    public ResponseEntity<Map<String, Long>> modifyCourse(@RequestBody CourseBoardDTO courseBoardDTO) {

        log.info("courseBoardDTO: " + courseBoardDTO);
        Map<String, Long> result = new HashMap<>();
        // token 없이 controller Test시 제거할 것
    if (!SecurityUtil.validateMno(courseBoardDTO.getWriter())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
        try {
            Long bno = boardService.modifyCourse(courseBoardDTO);
            result.put("bno", bno);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 장소, 코스 포스팅 정보 조회
    @GetMapping(value = {"/place/posting", "/course/posting"})
    public ResponseEntity<BoardDTO> getPlaceBoard(@RequestParam("bno") Long bno) {
        log.info("getPlaceBoard... bno: " + bno);
        BoardDTO boardDTO = boardService.getBoardByBno(bno);
        return new ResponseEntity<>(boardDTO, HttpStatus.OK);
    }

    // 보드 메인페이지 정보 조회
    @GetMapping(value = "/main")
    public ResponseEntity<ResponseWrapDTO<MainResponseDTO>> boardMain(@RequestBody MnoDTO mnoDTO) {
        ResponseWrapDTO response = new ResponseWrapDTO(true, boardService.mainBoard(mnoDTO.getMno()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //   회원별 장소 포스팅 정보 조회
    @GetMapping(value = {"/place/posting"})
    public ResponseEntity<List<BoardReplyCountDTO>> getBoardByMno(@RequestParam("mno") Long mno) {
        log.info("getBoardByMno... bno: " + mno);
        List<BoardReplyCountDTO> boardReplyCountDTO = boardService.getBoardByMno(mno);
        return new ResponseEntity<>(boardReplyCountDTO, HttpStatus.OK);
    }

    // 장소별 장소 포스팅 정보 조회
    @GetMapping(value = {"/place"})
    public ResponseEntity<List<BoardPlaceReplyCountDTO>> getBoardByPno(@RequestParam("pno") Long pno) {
        log.info("getBoardByMno... bno: " + pno);
        List<BoardPlaceReplyCountDTO> boardPlaceReplyCountDTO = boardService.getBoardByPno(pno);
        return new ResponseEntity<>(boardPlaceReplyCountDTO, HttpStatus.OK);
    }

    // 회원별 코스 포스팅 정보 조회
    @GetMapping(value = {"/course/posting"})
    public ResponseEntity<List<BoardReplyCountDTO>> getCourseBoardByMno(@RequestParam("mno") Long mno) {
        log.info("getBoardByMno... bno: " + mno);
        List<BoardReplyCountDTO> boardReplyCountDTO = boardService.getCourseBoardByMno(mno);
        return new ResponseEntity<>(boardReplyCountDTO, HttpStatus.OK);
    }

    // 코스 검색 조회
    @GetMapping(value = {"/course"})
    public ResponseEntity<List<BoardSearchDTO>> findCourseBoard(@RequestParam("search") String search) {
        log.info("Search.... : "+search);
        List<BoardSearchDTO> boardSearchDTO = boardService.findCourseBoard(search);
        return new ResponseEntity<>(boardSearchDTO, HttpStatus.OK);
    }

}
