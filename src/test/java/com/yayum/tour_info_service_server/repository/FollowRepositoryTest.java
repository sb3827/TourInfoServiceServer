package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Follow;
import com.yayum.tour_info_service_server.entity.FollowPK;
import com.yayum.tour_info_service_server.entity.Member;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FollowRepositoryTest {

  @Autowired
  FollowRepository followRepository;

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

  @Test
  public void getFollowerList() {
    Member member = Member.builder().mno(8L).build();
    List<Follow> result = followRepository.getFollowersByMember(member);
  }

  @Test
  public void getFollowingList() {
    Member member = Member.builder().mno(8L).build();
    List<Follow> result = followRepository.getMembersByFollower(member);
  }

  @Test
  @Transactional
  public void unFollow() {
    Member member = Member.builder().mno(8L).build();
    Member follower = Member.builder().mno(15L).build();
    followRepository.deleteByMemberAndFollower(member,follower);
  }
}