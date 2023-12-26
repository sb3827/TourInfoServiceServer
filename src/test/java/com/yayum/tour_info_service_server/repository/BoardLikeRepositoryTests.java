package com.yayum.tour_info_service_server.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardLikeRepositoryTests {

    @Autowired
    BoardLikeRepository boardLikeRepository;

    @Test
    void removeBoardLikeTest(){
        boardLikeRepository.removeBoardLike(9L);
    }

}