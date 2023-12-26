package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.BoardDTO;
import com.yayum.tour_info_service_server.dto.CourseBoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BoardServiceTests {

  @Autowired
  private BoardService boardService;

  @Test
  public void placeRegisterPost () {
    List<String> imagePaths = new ArrayList<>();
    imagePaths.add("이미지1 경로");
    imagePaths.add("이미지2 경로");

    BoardDTO boardDTO = BoardDTO.builder()
        .bno(4L)
        .title("제목3")
        .content("글1")
        .isAd(false)
        .isCourse(false)
        .score(0d)
        .likes(0)
        .writer(5L)
        .src(imagePaths)
        .pno(6L)
        .build();

    Long bno = boardService.placeRegister(boardDTO);
    System.out.println("bno: "+ bno);

  }

  @Test
  public void CourseRegisterPost () {
    List<String> imagePaths = new ArrayList<>();
    imagePaths.add("이미지1 경로");
    imagePaths.add("이미지2 경로");

    List<Long> pno = new ArrayList<>();
    pno.add(7L);
    pno.add(8L);
    pno.add(9L);

    CourseBoardDTO courseBoardDTO = CourseBoardDTO.builder()
        .bno(4L)
        .title("제목1")
        .content("글1")
        .isAd(false)
        .isCourse(true)
        .day(2)
        .orderNumber(2)
        .score(0d)
        .likes(0)
        .writer(5L)
        .src(null)
        .pno(pno)
        .build();
    Long bno = boardService.courseRegister(courseBoardDTO);
    System.out.println("bno: "+ bno);

  }

  @Test
  public void remove() {
     boardService.remove(2L);
  }

  @Test
  public void modify() {

//    List<String> imagePaths = new ArrayList<>();
//    imagePaths.add("이미지1 경로수정");
//    imagePaths.add("이미지2 경로수정4");
//    imagePaths.add("이미지3 경로수정");


    BoardDTO boardDTO = BoardDTO.builder()
        .bno(3L)
        .title("changed title2")
        .content("changed content2")
//        .src(imagePaths)
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