package com.dot.tour_info_service_server.service;


import com.dot.tour_info_service_server.dto.FollowDTO;
import com.dot.tour_info_service_server.entity.Follow;
import com.dot.tour_info_service_server.entity.FollowPK;
import com.dot.tour_info_service_server.entity.Member;

import java.util.List;

public interface FollowService {
  List<FollowDTO> getListOfFollower(Long mno);

  List<FollowDTO> getListOfFollowing(Long mno);

  void follow(FollowDTO followDTO);

  void unFollow(Long mno,Long follower);

  default FollowDTO entityToDto(Follow follow) {
    FollowDTO followDTO = FollowDTO.builder()
        .memberMno(follow.getFollowPk().getFollower().getMno())
        .followerMno(follow.getFollowPk().getMember().getMno())
        .build();
    return followDTO;
  }

  default Follow dtoToEntity(FollowDTO followDTO) {
    Follow follow = Follow.builder().
        followPk(FollowPK.builder()
            .member(Member.builder().mno(followDTO.getMemberMno()).build())
            .follower(Member.builder().mno(followDTO.getFollowerMno()).build())
            .build())
        .build();
    return follow;
  }

}
