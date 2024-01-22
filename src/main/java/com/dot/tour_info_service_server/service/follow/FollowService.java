package com.dot.tour_info_service_server.service.follow;


import com.dot.tour_info_service_server.dto.FollowDTO;
import com.dot.tour_info_service_server.dto.FollowResponseDTO;
import com.dot.tour_info_service_server.entity.follow.Follow;
import com.dot.tour_info_service_server.entity.follow.FollowPK;
import com.dot.tour_info_service_server.entity.Member;

import java.util.List;

public interface FollowService {
  List<FollowResponseDTO> getListOfFollower(Long mno);

  List<FollowResponseDTO> getListOfFollowing(Long mno);

  void follow(FollowDTO followDTO);

  void unFollow(Long mno,Long follower);


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