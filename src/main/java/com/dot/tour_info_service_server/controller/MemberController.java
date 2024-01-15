package com.dot.tour_info_service_server.controller;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.security.util.SecurityUtil;
import com.dot.tour_info_service_server.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    // 회원정보 조회 검증 필요
    @GetMapping(value = "/info")
    public ResponseEntity<UserInfoDTO> findUserInfo(@RequestParam("mno") Long mno) {
        log.info("findUserInfo........." + mno);
        if (SecurityUtil.validateMno(mno)) {
            UserInfoDTO userInfoDTO = memberService.showUserInfo(mno);
            return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //     회원정보 수정 검증 필요
    @PutMapping(value = "/info/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDTO> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        log.info("updateUserInfo.........");
        log.info("DTO: "+userInfoDTO);
        if (SecurityUtil.validateEmail(userInfoDTO.getEmail())) {
            UserInfoDTO changedUserInfo = memberService.modifyUserInfo(userInfoDTO);
            return new ResponseEntity<>(changedUserInfo, HttpStatus.OK);
        } else {
            log.error("token is not valid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 프로필 조회
    @GetMapping(value = "/profile")
    public ResponseEntity<UserProfileDTO> findUserProfile(@RequestParam("name") String name) {
        log.info("User Profile..........");
        UserProfileDTO userProfileDTO = memberService.showUserProfile(name);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }

    // 회원 탈퇴 검증 필요
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Map<String, Long>> removeUserInfo(@RequestParam("mno") Long mno) {
        log.info("User Delete......");
        Map<String, Long> result = new HashMap<>();
        if (SecurityUtil.validateMno(mno)) {
            memberService.deleteUserInfo(mno);
            result.put("mno", mno);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 검색
    @PostMapping(value = "/find")
    public ResponseEntity<List<SearchUserListDTO>> findUser(@RequestParam("search") String search,@RequestBody(required = false)MnoDTO mnoDTO) {
        log.info("Searching User.......");
        List<SearchUserListDTO> userlist;
        if(mnoDTO.getMno()==null){
            userlist=memberService.searchUser(search,null);
        }else {
            userlist = memberService.searchUser(search, mnoDTO.getMno());
        }
        return new ResponseEntity<>(userlist, HttpStatus.OK);
    }

    // 회원가입대기 조회 ( 관리자만 )
    @GetMapping(value = "/waiting")
    public ResponseEntity<List<JoinWaitingDTO>> showJoinWaiting() {
        log.info("JoinWaiting List.............");
        List<JoinWaitingDTO> list = memberService.showJoinWaiting();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 회원가입 승인 ( 관리자만 )
    @PutMapping(value = "/approve")
    public ResponseEntity<Map<String, Long>> joinMember(@RequestParam("mno") Long mno) {
        log.info("Join..............");
        Map<String, Long> result = new HashMap<>();
        memberService.joinMember(mno);
        result.put("mno", mno);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //회원 검색 관리자
    @GetMapping(value = "/filter-find")
    public ResponseEntity<List<MemberDetailDTO>> managerSearchUser(@RequestParam("filter") String filter, @RequestParam("search") String name) {
        log.info(filter + " , " + name);
        List<MemberDetailDTO> result = memberService.managerToSearchUser(filter, name);
        log.info(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}





