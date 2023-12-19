package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTests {

  @Autowired
  private ReplyRepository replyRepository;

  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private MemberRepository memberRepository;


  @Test
  public void insertReply() {
    IntStream.rangeClosed(1, 2).forEach(i -> {

      Member member = Member.builder().mno(11L).build();

      Board board = Board.builder().bno(12L).build();

        Reply reply = Reply.builder()
            .text("Reply...." + i)
            .board(board)
            .member(member)
            .build();
        replyRepository.save(reply);
    });
  }


}