package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReplyRepositoryTest {

  @Autowired
  ReplyRepository replyRepository;

  @Test
  public void insertReply() {

    Member member = Member.builder().mno(11L).build();

    Board board = Board.builder().bno(12L).build();

    Reply parent = Reply.builder().rno(10L).build();

    Reply reply = Reply.builder()
        .text("ReplyText...." )
        .board(board)
        .parent(parent)
        .member(member)
        .build();
    replyRepository.save(reply);
  }


  @Test
  public void getListByBno() {
    Board board = Board.builder().bno(12L).build();
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
    Member member = Member.builder().mno(11L).build();
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