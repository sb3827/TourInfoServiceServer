package com.yayum.tour_info_service_server.repository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTests {

    @Autowired
    BoardRepository boardRepository;

    // 게시물 삭제 테스트
    @Test
    void remove(){
        boardRepository.removeBoard(1L);
    }

    // 게시물 코스 게시글/ 장소 게시글 판별 테스트
    @Test
    void course(){
        System.out.println(boardRepository.boardIsCourse(5L));
    }

    // bno 반환 테스트
    @Test
    void returnBno(){
        System.out.println(boardRepository.returnBnos(26L));
    }

}