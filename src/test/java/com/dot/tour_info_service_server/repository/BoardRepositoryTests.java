package com.dot.tour_info_service_server.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}