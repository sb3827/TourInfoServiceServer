package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.FollowDTO;
import com.yayum.tour_info_service_server.entity.Follow;
import com.yayum.tour_info_service_server.entity.FollowPK;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
  private final FollowRepository followRepository;

  @Override
  public List<FollowDTO> getListOfFollower(Long mno) {
    Member member = Member.builder().mno(mno).build();
    List<Follow> result = followRepository.getFollowerByMember(member);
    return result.stream().map(follow -> entityToDto(follow)).collect(Collectors.toList());
  }

  @Override
  public void follow(FollowDTO followDTO) {
    Follow follow = dtoToEntity(followDTO);
    followRepository.save(follow);
  }

  @Override
  public void unFollow(FollowPK followPK) {
    followRepository.deleteById(followPK);
  }
}
