package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTests {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    public void testClass(){
        System.out.println(replyRepository.getClass().getName());
    }

    @Test
    void insertReplyDummies(){
        IntStream.rangeClosed(1, 5).forEach(i -> {

//            long bno = (long) (Math.random()* 3) + 10;
            Member member = Member.builder().mno(3L).build();
            Board board = Board.builder().bno(3L).build();

            Reply reply = Reply.builder().board(board).member(member).text("test Reply" + i).build();
            replyRepository.save(reply);
        });
    }

    @Test
    void removeReplyTest(){
        replyRepository.removeReply(12L);
    }

}