package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
class BoardRepositoryTests {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void remove() {
        boardRepository.removeBoard(1L);
    }

    @Test
    void course() {
        System.out.println(boardRepository.boardIsCourse(5L));
    }

    @Test
    void returnBno() {
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

    @Transactional
    @Test
    public void getBoardTest() {
        List<Object[]> result = boardRepository.getPlaceBoardByBno(3L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }


    @Transactional
    @Test
    public void getBoardByMnoTest() {
        List<Object[]> result = boardRepository.getBoardByMno(5L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void gettest() {
        List<Object[]> result = boardRepository.getBoardByPno(3L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }

    }

    @Test
    public void findCourse() {
        List<Object[]> result = boardRepository.findCourseBoard("test2");
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }
}