package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.entity.Place;
import com.dot.tour_info_service_server.repository.BoardRepository;
import com.dot.tour_info_service_server.repository.PlaceRepository;
import com.dot.tour_info_service_server.service.board.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BoardServiceTests {

  @Autowired
  private BoardService boardService;

  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private PlaceRepository placeRepository;

//  @Test
//  public void placeRegisterPost() {
//    List<String> imagePaths = new ArrayList<>();
//    imagePaths.add("이미지1 경로");
//    imagePaths.add("이미지2 경로");
//
//    PlaceBoardDTO placeBoardDTO = PlaceBoardDTO.builder()
//            .title("제목3")
//            .content("글1")
//            .isAd(false)
//            .isCourse(false)
//            .score(0d)
//            .likes(0)
//            .writer(5L)
//            .srcList(imagePaths)
//            .pno(6L)
//            .build();
//
////    Long bno = boardService.placeRegister(placeBoardDTO);
////    System.out.println("bno: "+ bno);
//
//  }

//  @Test
//  public void CourseRegisterPost() {
//    List<String> imagePaths = new ArrayList<>();
//    IntStream.rangeClosed(1, 5).forEach(i -> imagePaths.add("image" + i + " src"));
//
//    List<BoardPlacePKDTO> boardPlacePKDTO = new ArrayList<>();
//    IntStream.rangeClosed(1, 4).forEach(i -> {
//      BoardPlacePKDTO boardPlace = BoardPlacePKDTO.builder()
//              .pno((long) i)
//              .day((i + 1) % 2)
//              .orderNumber(i / 2)
//              .build();
//
//      boardPlacePKDTO.add(boardPlace);
//    });
//
//
//    CourseBoardDTO courseBoardDTO = CourseBoardDTO.builder()
//            .title("제목1")
//            .content("글1")
//            .isAd(false)
//            .isCourse(true)
//            .score(0d)
//            .likes(0)
//            .srcList(null)
//            .writer(5L)
//            .coursePlaceList(boardPlacePKDTO)
//            .build();
////    Long bno = boardService.courseRegister(courseBoardDTO);
////    System.out.println("bno: "+ bno);
//  }

  @Test
  public void remove() {
    boardService.remove(2L);
  }

//  @Test
//  public void modify() {
//
//    List<String> imagePaths = new ArrayList<>();
//    imagePaths.add("이미지1 경로수정1");
//    imagePaths.add("이미지2 경로수정4");
//    imagePaths.add("이미지3 경로수정5");
//
//    PlaceBoardDTO placeBoardDTO = PlaceBoardDTO.builder()
//            .bno(3L)
//            .title("changed title2")
//            .content("changed content2")
//            .srcList(imagePaths)
//            .build();
//    boardService.placeBoardModify(placeBoardDTO);
//
//  }

  @Test
  public void getBoardTest() {
//    BoardDTO dto =
//            boardService.getBoardByBno(3L);
//    System.out.println(dto);
  }

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
