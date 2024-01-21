package com.dot.tour_info_service_server.repository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@Log4j2
@SpringBootTest
class BoardRepositoryTests {

    @Autowired
    BoardRepository boardRepository;

    // 게시물 삭제 테스트
    @Test
    void remove() {
        boardRepository.removeBoard(1L);
    }

    // 게시물 코스 게시글/ 장소 게시글 판별 테스트
    @Test
    void course() {
        System.out.println(boardRepository.boardIsCourse(5L));
    }

    // bno 반환 테스트
    @Test
    void returnBno() {
        System.out.println(boardRepository.returnBnos(26L));
    }

    @Test
    void setNullTest(){
        boardRepository.setNullMno(16L);
    }


    @Test
    @Transactional
    void test2() {
        List<Object[]> result = boardRepository.getPlaceBoardByBno(8L);
        result.forEach(objects -> {
            System.out.println(Arrays.toString(objects));
        });
        for (Object[] objects: result) {
            System.out.println(Arrays.toString(objects));
        }

    }

    @Test
    void getPlaceBoardByBnoTest() {
//        Object result = boardRepository.getPlaceBoardByBno(1L);
//
//        System.out.println(result);
//
//        if(result.isEmpty()){
//            log.error("empty");
//            return;
//        }
//
//        log.info(result.get());

    }

    @Test
    void mostCourse(){
        List<Object[]> mlc=boardRepository.mostLikeCourse();
        for (Object[] objects:mlc){
            System.out.println(objects);
            System.out.println(boardRepository.mostLikeCoursePlace((Long)objects[0]));
        }
    }

}