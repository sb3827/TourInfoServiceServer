package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Reply;
import com.dot.tour_info_service_server.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
class ReplyRepositoryTests {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void testClass() {
        System.out.println(replyRepository.getClass().getName());
    }

    @Test
    void insertReplyDummies() {
        IntStream.rangeClosed(1, 5).forEach(i -> {

//            long bno = (long) (Math.random()* 3) + 10;
            Member member = Member.builder().mno(3L).build();
            Board board = Board.builder().bno(3L).build();

            Reply reply = Reply.builder().board(board).member(member).text("test Reply" + i).build();
            replyRepository.save(reply);
        });
    }

    @Test
    void removeReplyTest() {
        replyRepository.removeReply(12L);
    }

    @Test
    void showReplyListTest() {
        log.info("replyList : " + replyRepository.showReplyList(2L));
    }

    //bno를 삭제했을때 해당 rno가 삭제됨
    @Test
    void deleteByBnoTest() {
        replyRepository.deleteByBno(1L);
    }

    //bno를 삭제했을때 해당 rno가 삭제됨
    @Test
    void deleteByChildRnoTest() {
        replyRepository.deleteByChildRno(1L);
    }

}