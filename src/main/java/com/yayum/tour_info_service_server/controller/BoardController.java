package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.CourseBoardDTO;
import com.yayum.tour_info_service_server.security.util.SecurityUtil;
import com.yayum.tour_info_service_server.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;

    // 코스 게시글 수정 address
    @PutMapping("/course/posting/update")
    public ResponseEntity<Map<String, Long>> modifyCourse(CourseBoardDTO courseBoardDTO) {
        Map<String, Long> result = new HashMap<>();
        // token 없이 controller Test시 제거할 것
        if (!SecurityUtil.validateMno(courseBoardDTO.getWriter())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Long bno = boardService.modifyCourse(courseBoardDTO);
            result.put("bno", bno);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
