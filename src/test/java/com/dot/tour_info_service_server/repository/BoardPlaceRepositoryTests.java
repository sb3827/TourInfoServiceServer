package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.*;
import com.yayum.tour_info_service_server.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class BoardPlaceRepositoryTests {

    @Autowired
    BoardPlaceRepository boardPlaceRepository;

    @Test
    public void testClass(){
        System.out.println(boardPlaceRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies(){

        IntStream.rangeClosed(1, 1).forEach(i -> {
//            long bno = (long) (Math.random()* 3) + 10;
//            long pno = (long) (Math.random()* 5) + 20;

            Member member = Member.builder().mno(3L).build();
            Board board = Board.builder().bno(2L).writer(member).build();
            Place place = Place.builder().pno(4L).build();

            BoardPlacePK boardPlacePK = BoardPlacePK.builder().board(board).build();
//            BoardPlacePK boardPlacePK = BoardPlacePK.builder().day(3).orderNumber(3).board(board).build();
            BoardPlace boardPlace = BoardPlace.builder().boardPlacePK(boardPlacePK).place(place).build();
            boardPlaceRepository.save(boardPlace);
        });
    }

    @Test
    public void updateTest(){
        boardPlaceRepository.updateBoardPlacePno(24L);
    }

    @Test
    public void deteleTest(){
        boardPlaceRepository.removeBoardPlaceByPno(9L);
    }
}