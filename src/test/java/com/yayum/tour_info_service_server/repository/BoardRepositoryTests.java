package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTests {
  @Autowired
  private BoardRepository boardRepository;

  @Test
  public void insertDummies() {
    IntStream.rangeClosed(1, 5).forEach(i -> {
      Member member = Member.builder().mno((long)(i)).build();
      Board board = Board.builder()
          .title("title"+i)
          .content("content"+i)
          .score(Math.random()*i)
          .isAd(false)
          .isCourse(false)
          .likes(i)
          .writer(member)
          .build();

      System.out.println(board);
      boardRepository.save(board);
    });
  }
}