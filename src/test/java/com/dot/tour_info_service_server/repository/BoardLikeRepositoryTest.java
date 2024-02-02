package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Board;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class BoardLikeRepositoryTest {
    @Autowired
    private BoardLikeRepository boardLikeRepository;


    @Test
    @Transactional
    @Rollback(false)
    public void deleteTest(){
        boardLikeRepository.deleteAllByBoardLikePKBoard(Board.builder()
                .bno(8l)
                .build());
    }
}