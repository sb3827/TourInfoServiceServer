package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.dto.request.board.CourseBoardRequestDTO;
import com.dot.tour_info_service_server.dto.request.board.PlaceBoardRequestDTO;
import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Place;
import com.dot.tour_info_service_server.repository.BoardRepository;
import com.dot.tour_info_service_server.repository.PlaceRepository;
import com.dot.tour_info_service_server.service.board.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@SpringBootTest
class BoardServiceTests {

  @Autowired
  private BoardService boardService;

  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private PlaceRepository placeRepository;

  // 장소 게시글 등록
  @Test
  public void placeRegisterPost() {
    List<Long> image = new ArrayList<>();
    image.add(1L);

    PlaceBoardRequestDTO placeBoardRequestDTO = PlaceBoardRequestDTO.builder()
            .title("테스트")
            .content("테스트")
            .score(0d)
            .writer(1L)
            .place(1L)
            .deleteImages(null)
            .images(image)
            .build();

    Long bno = boardService.placeRegister(placeBoardRequestDTO);
    System.out.println("bno: "+ bno);

  }

  // 코스 게시글 등록
  @Test
  public void CourseRegisterPost() {
    List<Long> image = new ArrayList<>();
    image.add(1L);
    List<List<Long>> courseList = new ArrayList<>();
    courseList.add(new ArrayList<>());

    CourseBoardRequestDTO courseBoardRequestDTO = CourseBoardRequestDTO.builder()
            .title("테스트")
            .content("테스트")
            .score(0d)
            .writer(1L)
            .deleteImages(null)
            .images(image)
            .coursePlaceList(courseList)
            .build();

    Long bno = boardService.courseRegister(courseBoardRequestDTO);
    System.out.println("bno: "+ bno);
  }

  // 게시글 삭제
  @Test
  public void remove() {
    boardService.remove(2L);
  }


  // 장소 게시글 수정
  @Test
  public void placeBoardModify() {

    List<Long> image = new ArrayList<>();
    image.add(1L);

    PlaceBoardRequestDTO placeBoardRequestDTO = PlaceBoardRequestDTO.builder()
            .bno(3L)
            .title("changed title2")
            .content("changed content2")
            .place(1L)
            .score(0d)
            .writer(1L)
            .deleteImages(null)
            .images(image)
            .build();
    boardService.placeBoardModify(placeBoardRequestDTO);

  }

  // 코스 게시글 수정
  @Test
  public void courseBoardModify() {
    List<Long> image = new ArrayList<>();
    image.add(1L);
    List<List<Long>> courseList = new ArrayList<>();
    courseList.add(new ArrayList<>());

    CourseBoardRequestDTO courseBoardRequestDTO = CourseBoardRequestDTO.builder()
            .bno(3L)
            .title("changed title2")
            .content("changed content2")
            .coursePlaceList(courseList)
            .score(0d)
            .writer(1L)
            .deleteImages(null)
            .images(image)
            .build();
    try {
      boardService.modifyCourse(courseBoardRequestDTO);
    } catch (Exception e) {
      e.getMessage();
    }


  }

  // 장소 포스팅 조회
  @Test
  public void getBoardByBnoTest() {
    try {
      BoardInfoDTO boardInfoDTO = boardService.getBoardByBno(1L);
      log.info(boardInfoDTO);
    } catch (Exception e) {
      e.getMessage();
    }

  }

  // 코스 포스팅 조회
  @Test
  public void getCourseByBnoTest() {
    try {
      BoardInfoDTO boardInfoDTO = boardService.getCourseByBno(1L);
      log.info(boardInfoDTO);
    } catch (Exception e) {
      e.getMessage();
    }
  }

  // 회원별 장소 포스팅 정보 조회
  @Test
  public void getBoardByMnoTest() {
    List<BoardMemberDTO> result = boardService.getBoardByMno(1L);
      System.out.println(result);
  }

  // 장소별 장소 포스팅 조회
  @Test
  public void getBoardByPnoTest() {
    try {
      List<BoardPlaceReplyCountDTO> result = boardService.getBoardByPno(1L);
      System.out.println(result);
    } catch (Exception e) {
      e.getMessage();
    }
  }

  // 회원별 장소 포스팅 정보 조회
  @Test
  public void getCourseBoardByMnoTest() {
    List<BoardMemberDTO> result = boardService.getCourseBoardByMno(1L);
    System.out.println(result);
  }

  // 코스 검색 조회
  @Test
  public void findCourseBoardTest() {
    List<BoardSearchDTO> result = boardService.findCourseBoard("");
    System.out.println(result);
  }


  //메인 페이지 게시글 조회
  @Test
  public void mainTest() {
    Long mno = 2l;

    List<Object[]> result = placeRepository.mostLikePlace();
    List<MainPlaceResponseDTO> mainPlaceResponseDTOS = new ArrayList<>();
    for (Object[] objects : result) {
      MainPlaceResponseDTO mainPlaceResponseDTO = MainPlaceResponseDTO.builder()
              .pno((Long) objects[0])
              .name((String) objects[1])
              .src((String) objects[2])
              .build();
      mainPlaceResponseDTOS.add(mainPlaceResponseDTO);
    }

    List<Object[]> result1 = boardRepository.recentlyBoard();
    List<MainBoardResponseDTO> mainBoardResponseDTOS = new ArrayList<>();
    for (Object[] objects : result1) {
      MainBoardResponseDTO mainBoardResponseDTO = MainBoardResponseDTO.builder()
              .bno((Long) objects[0])
              .title((String) objects[1])
              .src((String) objects[2])
              .build();
      mainBoardResponseDTOS.add(mainBoardResponseDTO);
    }

    List<Object[]> result2 = boardRepository.mostLikeCourse();
    List<MostListCourseDTO> mostListCourseDTOS = new ArrayList<>();
    for (Object[] objects : result2) {
      MainBoardResponseDTO mainBoardResponseDTO = MainBoardResponseDTO.builder()
              .bno((Long) objects[0])
              .title((String) objects[1])
              .src((String) objects[2])
              .isCourse((Boolean) objects[3])
              .build();
      List<Place> places=boardRepository.mostLikeCoursePlace((Long)objects[0]);
      List<MainPlaceDTO> mainPlaceDTOS=new ArrayList<>();
      for (Place place:places){
        MainPlaceDTO mainPlaceDTO=MainPlaceDTO.builder()
                .name(place.getName())
                .lat(place.getLat())
                .lng(place.getLng())
                .roadAddress(place.getRoadAddress())
                .engAddress(place.getEngAddress())
                .localAddress(place.getLocalAddress())
                .build();
        mainPlaceDTOS.add(mainPlaceDTO);
      }

      MostListCourseDTO mostListCourseDTO=MostListCourseDTO.builder()
              .mainBoardResponseDTO(mainBoardResponseDTO)
              .placeList(mainPlaceDTOS)
              .build();
      mostListCourseDTOS.add(mostListCourseDTO);
    }


    List<Object[]> result3 = boardRepository.followerBoard(mno);
    List<MainBoardResponseDTO> mainBoardResponseDTOS2 = new ArrayList<>();
    for (Object[] objects : result3) {
      MainBoardResponseDTO mainBoardResponseDTO = MainBoardResponseDTO.builder()
              .bno((Long) objects[0])
              .title((String) objects[1])
              .src((String) objects[2])
              .build();
      mainBoardResponseDTOS2.add(mainBoardResponseDTO);
    }

    List<Object[]> result4 = boardRepository.adBoard();
    List<MainBoardResponseDTO> mainBoardResponseDTOS3 = new ArrayList<>();
    for (Object[] objects : result4) {
      MainBoardResponseDTO mainBoardResponseDTO = MainBoardResponseDTO.builder()
              .bno((Long) objects[0])
              .title((String) objects[1])
              .src((String) objects[2])
              .build();
      mainBoardResponseDTOS3.add(mainBoardResponseDTO);
    }

    MainResponseDTO mainResponseDTO = new MainResponseDTO(mainPlaceResponseDTOS, mainBoardResponseDTOS, mostListCourseDTOS, mainBoardResponseDTOS2, mainBoardResponseDTOS3);

    System.out.println(mainResponseDTO);

  }
}
