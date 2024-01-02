package com.dot.tour_info_service_server.repository;

<<<<<<< HEAD:src/test/java/com/yayum/tour_info_service_server/repository/BoardRepositoryTests.java
=======
>>>>>>> 207487ec6237182fd570d05ca0a9c4f91e640377:src/test/java/com/dot/tour_info_service_server/repository/BoardRepositoryTests.java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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