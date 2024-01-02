package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Reply;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
class ReplyRepositoryTest {

  @Autowired
  ReplyRepository replyRepository;

  @Test
  public void insertParentReply() {

    Member member = Member.builder().mno(1L).build();

    Board board = Board.builder().bno(2L).build();


    Reply reply = Reply.builder()
        .text("firstParentReplyBy1" )
        .board(board)
        .member(member)
        .build();
    replyRepository.save(reply);
  }

  @Test
  public void insertChildReply() {

    Member member = Member.builder().mno(2L).build();

    Board board = Board.builder().bno(2L).build();

    Reply parent = Reply.builder().rno(3L).build();

    Reply reply = Reply.builder()
        .text("childReply44" )
        .board(board)
        .member(member)
        .parent(parent)
        .build();
    replyRepository.save(reply);
  }


  @Test
  public void getListByBno() {
    Board board = Board.builder().bno(2L).build();
    List<Reply> replies = replyRepository.getRepliesByBoardOrderByRnoAsc(board);
    replies.forEach(reply -> {
      System.out.println("rno : "+reply.getRno());
      System.out.println("text : "+reply.getText());
      System.out.println("regDate : "+reply.getRegDate());
      System.out.println("modDate : "+reply.getModDate());
      System.out.println("mno : "+reply.getMember().getMno());
      System.out.println("=======================================");
    });
  }


  @Test
  public void getListByMno() {
    Member member = Member.builder().mno(1L).build();
    List<Reply> replies = replyRepository.getRepliesByMemberOrderByRegDate(member);
    replies.forEach(reply -> {
      System.out.println("rno : "+reply.getRno());
      System.out.println("text : "+reply.getText());
      System.out.println("regDate : "+reply.getRegDate());
      System.out.println("modDate : "+reply.getModDate());
      System.out.println("mno : "+reply.getMember().getMno());
      System.out.println("=======================================");
    });
  }

}