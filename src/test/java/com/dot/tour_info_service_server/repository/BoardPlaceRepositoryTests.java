package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.*;
import com.dot.tour_info_service_server.entity.boardPlace.BoardPlace;
import com.dot.tour_info_service_server.entity.boardPlace.BoardPlacePK;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
class BoardPlaceRepositoryTests {

  @Autowired
  BoardPlaceRepository boardPlaceRepository;

  @Test
  public void testClass() {
    System.out.println(boardPlaceRepository.getClass().getName());
  }

  @Test
  public void testInsertDummies() {

    IntStream.rangeClosed(1, 1).forEach(i -> {
//            long bno = (long) (Math.random()* 3) + 10;
//            long pno = (long) (Math.random()* 5) + 20;

      Member member = Member.builder().mno(1L).build();
      Board board = Board.builder().bno(8L).writer(member).build();
      Place place = Place.builder().pno(4L).build();

      BoardPlacePK boardPlacePK = BoardPlacePK.builder().board(board).build();
//            BoardPlacePK boardPlacePK = BoardPlacePK.builder().day(3).orderNumber(3).board(board).build();
      BoardPlace boardPlace = BoardPlace.builder().boardPlacePK(boardPlacePK).place(place).build();
      boardPlaceRepository.save(boardPlace);
    });
  }

  @Test
  public void updateTest() {
    boardPlaceRepository.updateBoardPlacePno(24L);
  }

  @Test
  public void deteleTest() {
    boardPlaceRepository.removeBoardPlaceByPno(9L);
  }

  @Test
  public void deleteTestByBno() {
    boardPlaceRepository.deleteByBno(11L);
  }


  @Test
  public void loadRepresentImgTest() {
    try {
      List<Object[]> result = boardPlaceRepository.loadRepresentPlaceImageByPno(1L);
      if (!result.isEmpty())
        log.info(Arrays.toString(result.get(0)));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  @Test
  public void loadCourseListTest(){
    List<Object[]> result = boardPlaceRepository.loadListByBno(28L);

    if(!result.isEmpty()){
      List<List<Object[]>> dto = new ArrayList<>();
      dto.add(new ArrayList<>());
      int cnt = 0;
      for (Object[] tuple : result){
        log.info(Arrays.toString(tuple));
        if(cnt != (Integer) tuple[0]){
          dto.add(new ArrayList<>());
          cnt++;
        }
        dto.get((Integer) tuple[0]).add(tuple);
      }
//      log.info(Arrays.toString(dto.get(0).get(0)));
//      log.info(Arrays.toString(dto.get(0).get(1)));
//      log.info(Arrays.toString(dto.get(1).get(0)));
//      log.info(Arrays.toString(dto.get(1).get(1)));
    }
  }
}