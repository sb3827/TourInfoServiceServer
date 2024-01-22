package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.*;
import com.dot.tour_info_service_server.entity.boardLike.BoardLike;
import com.dot.tour_info_service_server.entity.boardLike.BoardLikePK;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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



}