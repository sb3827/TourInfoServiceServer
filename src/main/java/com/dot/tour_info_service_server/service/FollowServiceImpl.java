package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.FollowDTO;
import com.dot.tour_info_service_server.entity.Follow;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.repository.FollowRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
  private final FollowRepository followRepository;

  @Override
  public List<FollowDTO> getListOfFollower(Long mno) {
    Member member = Member.builder().mno(mno).build();
    List<Object[]> result = followRepository.getFollowersByMember(member);
    List<FollowDTO> followerList = new ArrayList<>();
    if (!result.isEmpty()) {
      for (Object[] list : result) {
        FollowDTO followDTO = FollowDTO.builder()
            .memberMno((long)list[0])
            .name((String) list[1])
            .build();
        followerList.add(followDTO);
      }
      return followerList;
    }
    return null;
  }

  @Override
  public List<FollowDTO> getListOfFollowing(Long mno) {
    Member member = Member.builder().mno(mno).build();
    List<Object[]> result = followRepository.getMembersByFollower(member);
    List<FollowDTO> followingList = new ArrayList<>();
    if (!result.isEmpty()){
      for (Object[] list : result) {
        FollowDTO followDTO = FollowDTO.builder()
            .followerMno((Long)list[0])
            .name((String) list[1])
            .build();
        followingList.add(followDTO);
      }
      return followingList;
    }
    return null;
  }

  @Override
  public void follow(FollowDTO followDTO) {
    Follow follow = dtoToEntity(followDTO);
    followRepository.save(follow);
  }

  @Override
  @Transactional
  public void unFollow(Long mno,Long follower) {
    followRepository.deleteByMemberAndFollower(Member.builder().mno(mno).build(), Member.builder().mno(follower).build());
  }
}
