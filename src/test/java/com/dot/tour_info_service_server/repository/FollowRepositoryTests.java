package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.follow.Follow;
import com.dot.tour_info_service_server.entity.follow.FollowPK;
import com.dot.tour_info_service_server.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class FollowRepositoryTests {

  @Autowired
  FollowRepository followRepository;

  // 팔로우
  @Test
  public void follow() {
    Member member = Member.builder().mno(15L).build();
    Member follower = Member.builder().mno(12L).build();

    Follow follow = Follow.builder()
        .followPk(FollowPK.builder()
            .follower(follower)
            .member(member)
            .build())
        .build();
    followRepository.save(follow);
  }

  //  언팔로우
  @Test
  @Transactional
  public void unFollow() {
    Member member = Member.builder().mno(8L).build();
    Member follower = Member.builder().mno(15L).build();
    followRepository.deleteByMemberAndFollower(member, follower);
  }

  // 팔로워 리스트 호출
  @Test
  public void getFollowerList() {
    Member member = Member.builder().mno(1L).build();
    List<Object[]> result = followRepository.getFollowersByMember(member);
    System.out.println("=======================FollowerList=======================");
    for (Object[] item : result) {
      System.out.println("* " + Arrays.toString(item));
    }
    System.out.println("===========================================================");
  }

  // 팔로잉 리스트 호출
  @Test
  public void getFollowingList() {
    Member member = Member.builder().mno(2L).build();
    List<Object[]> result = followRepository.getMembersByFollower(member);
    System.out.println("=======================FollowingList=======================");
    for (Object[] item : result) {
      System.out.println("* " + Arrays.toString(item));
    }
    System.out.println("===========================================================");
  }
}