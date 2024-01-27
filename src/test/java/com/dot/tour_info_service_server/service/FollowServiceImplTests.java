package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.FollowDTO;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.service.follow.FollowService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class FollowServiceImplTests {
    @Autowired
    private FollowService followService;

    @Test
    void getListOfFollower() {
        log.info(followService.getListOfFollower(2L));
    }

    @Test
    void getListOfFollowing() {
        log.info(followService.getListOfFollowing(3L));
    }

    @Test
    void follow() {
        followService.follow(FollowDTO.builder()
                        .followerMno(2L)
                        .memberMno(1L)
                .build());
    }

    @Test
    void unFollow() {
        followService.unFollow(1L,2L);
    }
}