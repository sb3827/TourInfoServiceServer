package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.UserInfoDTO;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Role;
import com.dot.tour_info_service_server.repository.MemberRepository;
import com.dot.tour_info_service_server.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;


@SpringBootTest
@Log4j2
class MemberServiceImplTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    // 회원정보 조회 테스트
    @Test
    void userInfoTest(){
        log.info("22L : " + memberService.showUserInfo(22L));
    }

    // 회원정보 수정 테스트
    @Test
    void modifyTest() {
        LocalDate date = LocalDate.of(2023, 12, 31);
        UserInfoDTO userInfoDTO = new UserInfoDTO(5L, null, "문영현", "v@a.com", "010-0000-1111", date, Role.MEMBER, true);
        Optional<Member> result = memberRepository.findById(userInfoDTO.getMno());
        if(result.isPresent()){
            Member member = result.get();
                member.changeName(userInfoDTO.getName());
                member.changePhone(userInfoDTO.getPhone());
                member.changeImage(userInfoDTO.getImage());
            memberRepository.save(member);
        }

    }

    // 회원 프로필 조회 테스트
    @Test
    void showUserProfileTest(){
        log.info(memberService.showUserProfile(2L));
    }

    // 회원 검색 테스트
    @Test
    void searchUserTest(){
        log.info(memberService.searchUser("이해창",2l, 0));
    }

    // 회원가입 대기 조회 테스트
    @Test
    void showJoinWaitingTest(){
        log.info(memberService.showJoinWaiting());
    }

    // 회원가입 승인 테스트
    @Test
    void joinMemberTest(){
        memberService.joinMember(5L);
    }

    // 회원탈퇴 테스트
    @Test
    void removeMemberTest(){
        memberService.deleteUserInfo(16L);
    }

    @Test
    void managerSearchTest(){
        log.info(memberService.managerToSearchUser("all",""));
    }


}


