package com.dot.tour_info_service_server.service;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class LikeServiceImplTest {
    @Autowired
    LikeService likeService;

    @Test
    public void likeBoardTest() {
        try {
            log.info(likeService.likeBoard(5L, 3L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unLikeBoardTest() {
        try {
            log.info(likeService.unLikeBoard(5L, 3L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}