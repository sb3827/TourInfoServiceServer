package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.Place;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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


    // 장소 포스팅 정보 조회
    @Test
    @Transactional
    void getPlaceBoardByBno() {
       Board result = boardRepository.getPlaceBoardByBno(8L);
        System.out.println(result);
    }

    // 코스 포스팅 정보 조회
    @Test
    void getCourseBoardByBnoTest() {
        Board result = boardRepository.getCourseBoardByBno(8L);
        System.out.println(result);
    }

    //최근 올라온 장소 게시글 10개
    @Test
    void recentlyBoardTest() {
        List<Object[]> result = boardRepository.recentlyBoard();
        for (Object[] objects: result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    // 가장 많은 추천을 받은 코스 게시글
    @Test
    void mostCourseTest(){
        List<Object[]> mlc=boardRepository.mostLikeCourse();
        for (Object[] objects:mlc){
            System.out.println(objects);
            System.out.println(boardRepository.mostLikeCoursePlace((Long)objects[0]));
        }
    }

    // 코스에 해당하는 1일차 코스
    @Test
    void getmostLikeCoursePlaceTest() {
        List<Object[]> result = boardRepository.recentlyBoard();
        for (Object[] objects: result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    //코스에 해당하는 1일차 코스
    @Test
    void mostLikeCoursePlace() {
        List<Place> result = boardRepository.mostLikeCoursePlace(9L);
        for (Place place : result) {
            System.out.println(place);
        }
    }

    //팔로워들의 게시글
    @Test
    void followerBoardTest() {
        List<Object[]> result = boardRepository.followerBoard(1L);
        for (Object[] objects: result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    //광고 게시글
    @Test
    void adBoardTest() {
        List<Object[]> result = boardRepository.adBoard();
        for (Object[] objects: result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    // 회원별 장소 포스팅 조회
    @Test
    void getBoardByMnoTest() {
        List<Object[]> result = boardRepository.getBoardByMno(1L);
        for (Object[] objects: result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    //장소별 장소 포스팅 조회
    @Test
    void getBoardByPnoTest() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Object[]> result = boardRepository.getBoardByPno(1L, pageRequest);
        for (Object[] objects: result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    // 회원별 코스 포스팅 조회
    @Test
    void getCourseBoardByMno() {
        List<Object[]> result = boardRepository.getCourseBoardByMno(1L);
        for (Object[] objects: result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    //코스 검색 조회
    @Test
    void findCourseBoardTest() {
        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<Object[]> result = boardRepository.findCourseBoard("",pageRequest);
        for (Object[] objects: result) {
            System.out.println(Arrays.toString(objects));
        }
    }

}