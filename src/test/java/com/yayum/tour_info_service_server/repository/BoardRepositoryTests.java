package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTests {
  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  public void insertDummies() {

    IntStream.rangeClosed(1, 5).forEach(i -> {

      Member member = Member.builder().mno(1L).build();

        Board board = Board.builder()
            .title("title" + i)
            .content("content" + i)
            .score(Math.random() * i)
            .isAd(false)
            .isCourse(false)
            .likes(i)
            .writer(member)
            .build();
        boardRepository.save(board);
    });
  }

  @Transactional
  @Test
  public void getPlaceBoardTest() {
    List<Board> result = boardRepository.getPlaceBoard(3L);
    System.out.println(result);
  }
}