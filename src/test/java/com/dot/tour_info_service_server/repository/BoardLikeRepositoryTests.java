package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
class BoardLikeRepositoryTests {

    @Autowired
    BoardLikeRepository boardLikeRepository;

    @Test
    void removeBoardLikeTest(){
        boardLikeRepository.removeBoardLIkeByMno(16L);
//        boardLikeRepository.removeBoardLike(9L);
    }

    @Test
    public void existsTest() {
        log.info(boardLikeRepository.existsByBoardLikePK(BoardLikePK.builder()
                .board(Board.builder().bno(8L).build())
                .member(Member.builder().mno(2L).build())
                .build()));
    }

    @Test
  public void insert() {
    IntStream.rangeClosed(1, 5).forEach(i -> {

      long bno = (long) (Math.random() * 3) + 10;

      Board board = Board.builder().bno(8L).build();

      BoardLikePK boardLikePK = BoardLikePK.builder()
          .board(board)
          .member(null)
          .build();

      BoardLike boardLike = BoardLike.builder()
          .boardLikePK(boardLikePK)
          .build();
      boardLikeRepository.save(boardLike);
    });
  }

  @Test
  public void deleteTest () {
    boardLikeRepository.deleteByBno(12L);
  }

  @Test
    void test() {
      List<Object[]> result = boardLikeRepository.getBoardLikeByBno(8L);
      for (Object[] objects: result) {
          System.out.println(Arrays.toString(objects));
      }
  }

}