package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.request.follow.FollowRequestDTO;
import com.dot.tour_info_service_server.service.follow.FollowService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class FollowServiceImplTests {
  @Autowired
  private FollowService followService;

  // 팔로워 목록 조회
  @Test
  void getListOfFollower() {
    log.info(followService.getListOfFollower(2L));
  }

  // 팔로잉 목록 조회
  @Test
  void getListOfFollowing() {
    log.info(followService.getListOfFollowing(3L));
  }

  // 회원 팔로우
  @Test
  void follow() {
    followService.follow(FollowRequestDTO.builder()
        .followerMno(2L)
        .memberMno(1L)
        .build());
  }

  // 회원 언팔로우
  @Test
  void unFollow() {
    followService.unFollow(1L, 2L);
  }
}