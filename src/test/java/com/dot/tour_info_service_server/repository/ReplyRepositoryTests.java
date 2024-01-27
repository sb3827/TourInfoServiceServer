package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Reply;
import com.dot.tour_info_service_server.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    void removeReplyTest() {
        replyRepository.removeReply(12L);
        replyRepository.removeChildReply(4L);
    }

    @Test
    void countByParent(){
        log.info("count : "+replyRepository.countAllByParent(Reply.builder().rno(8L).build()));
    }

    @Test
    public void showParentReplyTest(){
        List<Object[]>result=replyRepository.getParentReply(1l);
        System.out.println(result);

    }

    @Test
    public void getListByMno() {
        Member member = Member.builder().mno(1L).build();
        List<Reply> replies = replyRepository.getRepliesByMemberOrderByRegDate(member);
        replies.forEach(reply -> {
            System.out.println("rno : " + reply.getRno());
            System.out.println("text : " + reply.getText());
            System.out.println("regDate : " + reply.getRegDate());
            System.out.println("modDate : " + reply.getModDate());
            System.out.println("mno : " + reply.getMember().getMno());
            System.out.println("=======================================");
        });
    }


    @Test
    public void insertParentReply() {

        Member member = Member.builder().mno(1L).build();

        Board board = Board.builder().bno(2L).build();


        Reply reply = Reply.builder()
                .text("firstParentReplyBy1")
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
                .text("childReply44")
                .board(board)
                .member(member)
                .parent(parent)
                .build();
        replyRepository.save(reply);
    }


    @Test
    public void testClass() {
        System.out.println(replyRepository.getClass().getName());
    }



    @Test
    void showReplyListTest() {
        log.info("replyList : " + replyRepository.showReplyList(2L));
    }


}