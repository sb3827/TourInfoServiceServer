package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.entity.Member;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import java.util.List;

public interface MemberService {

    // 회원 정보 수정 페이지에 회원 정보 조회 이미지, 이름, 이메일, 전화번호, 생년월일, role
    UserInfoDTO showUserInfo(Long mno);


    // 회원정보 수정 ( 이미지, 이름, 전화번호 )
    UserInfoDTO modifyUserInfo(UserInfoDTO userInfoDTO);

    // 회원 프로필 조회 ( 이미지, 이름, 팔로잉, 팔로워, 찜목록 수 )
    UserProfileDTO showUserProfile(String name);

    // 회원 탈퇴
    void deleteUserInfo(Long mno);

    // 회원 검색 ( mno, 이미지, 이름 )
    List<SearchUserListDTO> searchUser(String name);

    // 회원가입대기 ( mno, 이름, 이메일, 사업자등록번호 )
    List<JoinWaitingDTO> showJoinWaiting();

    // 회원가입승인 ( is_approve를 true로 )
    void joinMember(Long mno);

    default UserInfoDTO entityToDto(Member member){
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .mno(member.getMno())
                .image(member.getImage())
                .birth(member.getBirth())
                .email(member.getEmail())
                .phone(member.getPhone())
                .name(member.getName())
                .build();
        return userInfoDTO;
    }
    default Member DtoToEntity(UserInfoDTO userInfoDTO){
        Member member = Member.builder()
                .mno(userInfoDTO.getMno())
                .name(userInfoDTO.getName())
                .phone(userInfoDTO.getPhone())
                .birth(userInfoDTO.getBirth())
                .build();
        return member;
    }


}
