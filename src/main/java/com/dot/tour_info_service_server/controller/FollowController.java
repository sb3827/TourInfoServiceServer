package com.dot.tour_info_service_server.controller;

import com.dot.tour_info_service_server.dto.FollowDTO;
import com.dot.tour_info_service_server.dto.FollowResponseDTO;
import com.dot.tour_info_service_server.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// 전체 authenticated
@RequestMapping("/follow")
@Log4j2
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    //   팔로우 버튼을 누르는 회원 -> followerMno    팔로우 당하는 회원-> memberMno
    @PostMapping("/following")
    public ResponseEntity<Map<String, Long>> follow(@RequestBody FollowDTO followDTO) {
        log.info("follow : " + followDTO);
        Map<String, Long> result = new HashMap<>();
        Long mno = followDTO.getMemberMno();
        result.put("mno", mno);
        followService.follow(followDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //   언팔로우 버튼을 누르는 회원 -> followerMno    언팔로우 당하는 회원-> memberMno
    @DeleteMapping("/following")
    public ResponseEntity<Map<String, Long>> unFollow(@RequestBody FollowDTO followDTO) {
        log.info("unfollow : " + followDTO);
        followService.unFollow(followDTO.getMemberMno(), followDTO.getFollowerMno());
        Map<String, Long> result = new HashMap<>();
        result.put("mno", followDTO.getMemberMno());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원이 팔로우 중인 사람들 조회(팔로잉 조회)        조회대상회원 -> memberMno 팔로잉->followerMno 로 나옴
    @GetMapping("/following")
    public ResponseEntity<List<FollowResponseDTO>> getListOfFollowing(@RequestParam("mno") Long mno) {
        log.info("List of Following : " + mno);
        List<FollowResponseDTO> result = followService.getListOfFollowing(mno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 회원을 팔로우 중인 사람들 조회(팔로워조회)       조회대상회원-> followerMno  팔로워->memberMno 로 나옴
    @GetMapping("/follower")
    public ResponseEntity<List<FollowResponseDTO>> getListOfFollower(@RequestParam("mno") Long mno) {
        log.info("List of follower : " + mno);
        List<FollowResponseDTO> result = followService.getListOfFollower(mno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
