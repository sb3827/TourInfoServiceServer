package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

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
  @Transactional
  public void selectByBno() {
    List<BoardPlace> result = boardPlaceRepository.selectBPbyBno(3L);

    System.out.println(result);
  }

}