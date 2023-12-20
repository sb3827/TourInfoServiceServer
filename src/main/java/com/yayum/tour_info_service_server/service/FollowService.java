package com.yayum.tour_info_service_server.service;


import com.yayum.tour_info_service_server.dto.FollowDTO;
import com.yayum.tour_info_service_server.entity.Follow;
import com.yayum.tour_info_service_server.entity.FollowPK;
import com.yayum.tour_info_service_server.entity.Member;

import java.util.List;

public interface FollowService {
  List<FollowDTO> getListOfFollower(Long mno);


  void follow(FollowDTO followDTO);

  void unFollow(FollowPK followPK);

  default FollowDTO entityToDto(Follow follow) {
    FollowDTO followDTO = FollowDTO.builder()
        .mno(follow.getFollowPk().getFollower().getMno())
        .followerMno(follow.getFollowPk().getMember().getMno())
        .build();
    return followDTO;
  }

  default Follow dtoToEntity(FollowDTO followDTO) {
    Follow follow = Follow.builder().
        followPk(FollowPK.builder()
            .member(Member.builder().mno(followDTO.getMno()).build())
            .follower(Member.builder().mno(followDTO.getFollowerMno()).build())
            .build())
        .build();
    return follow;
  }

}
