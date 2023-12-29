package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Follow;
import com.yayum.tour_info_service_server.entity.FollowPK;
import com.yayum.tour_info_service_server.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowPK> {

  @Query("SELECT f FROM Follow f WHERE f.followPk.member = :member")
  List<Follow> getFollowersByMember(@Param("member") Member follower);

  @Query("SELECT f FROM Follow f WHERE f.followPk.follower = :follower")
  List<Follow> getMembersByFollower(@Param("follower") Member member);

  @Modifying
  @Query("DELETE FROM Follow f WHERE f.followPk.member = :member AND f.followPk.follower = :follower")
  void deleteByMemberAndFollower(@Param("member") Member member, @Param("follower") Member follower);


}
