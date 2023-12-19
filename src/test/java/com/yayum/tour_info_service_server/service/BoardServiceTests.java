package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.BoardDTO;
import com.yayum.tour_info_service_server.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BoardServiceTests {

  @Autowired
  private BoardService boardService;

  @Test
  public void registerPost () {
    BoardDTO boardDTO = BoardDTO.builder()
        .bno(1L)
        .title("제목1")
        .content("글1")
        .isAd(false)
        .isCourse(false)
        .score(0d)
        .likes(0)
        .writer(2L)
        .build();

    Long bno = boardService.register(boardDTO);
    System.out.println("bno: "+ bno);

  }

  @Test
  public void remove() {
     boardService.remove(1L);
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

  @Test
  public void getTest() {
    BoardDTO dto = boardService.getPlaceBoardByBno(3L);
    System.out.println(dto);
  }
 }