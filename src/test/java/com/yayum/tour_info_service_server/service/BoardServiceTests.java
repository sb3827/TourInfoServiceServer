package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.BoardDTO;
import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class BoardServiceTests {

  @Autowired
  private BoardService boardService;

  @Test
  public void registerPost () {
    List<String> imagePaths = new ArrayList<>();
    imagePaths.add("이미지1 경로");
    imagePaths.add("이미지2 경로");

    BoardDTO boardDTO = BoardDTO.builder()
        .bno(30L)
        .title("제목3")
        .content("글1")
        .isAd(false)
        .isCourse(false)
        .score(0d)
        .likes(0)
        .writer(2L)
        .src(null)
        .pno(7L)
        .build();

    Long bno = boardService.register(boardDTO);
    System.out.println("bno: "+ bno);

  }

  @Test
  public void remove() {
     boardService.remove(3L);
  }

  @Test
  public void modify() {
    BoardDTO boardDTO = BoardDTO.builder()
        .bno(1L)
        .title("changed title")
        .content("changed content")
        .build();
    boardService.modify(boardDTO);

  }

//  @Test
//  public void getPlaceBoardTest() {
//    BoardDTO dto = boardService.getPlaceBoardByBno(3L);
//    System.out.println(dto);
//  }
//
//  @Test
//  public void getCourseBoardTest() {
//    BoardDTO dto = boardService.getCourseBoardByBno(7L);
//    System.out.println(dto);
//  }

//  @Test
//  public void getBoardByMnoTest() {
//    List<BoardDTO> dto = boardService.getBoardByMno(1L);
//    System.out.println(dto);
//  }

 }