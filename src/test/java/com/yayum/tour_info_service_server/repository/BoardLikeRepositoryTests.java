package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardLikeRepositoryTests {

  @Autowired
  private BoardLikeRepository boardLikeRepository;

  @Test
  public void insert() {
    IntStream.rangeClosed(1, 5).forEach(i -> {

      long bno = (long) (Math.random() * 3) + 10;

      Board board = Board.builder().bno(1L).build();

      Member member = Member.builder().mno(1L).build();

      BoardLikePK boardLikePK = BoardLikePK.builder()
          .board(board)
          .member(member)
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