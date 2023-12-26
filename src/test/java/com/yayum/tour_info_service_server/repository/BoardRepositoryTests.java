package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTests {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void remove(){
        boardRepository.removeBoard(1L);
    }

    @Test
    void course(){
        System.out.println(boardRepository.boardIsCourse(5L));
    }

    @Test
    void returnBno(){
        System.out.println(boardRepository.returnBnos(26L));
    }


//    @Test
//    void removeTest(){
//        List<Long> bnosToRemove = boardRepository.returnBnos(4L);
//
//        for ( Long bnos : bnosToRemove){
//            boardRepository.deleteById(bnos);
//        }
//    }



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
            .isCourse(true)
            .likes(i)
            .writer(member)
            .build();
        boardRepository.save(board);
    });
  }

//  @Transactional
//  @Test
//  public void getPlaceBoardTest() {
//    List<Object[]> result = boardRepository.getPlaceBoardByBno(3L);
//    System.out.println(result);
//  }
//
//  @Transactional
//  @Test
//  public void getCoarseBoardTest() {
//    List<Object[]> result = boardRepository.getCourseBoardByBno(8L);
//    System.out.println(result);
//  }

//  @Transactional
//  @Test
//  public void getBoardByMnoTest() {
//    List<Object[]> result = boardRepository.getBoardByMno(1L);
//    System.out.println(result);
//  }
}