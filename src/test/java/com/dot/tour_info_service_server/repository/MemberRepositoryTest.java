package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Role;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    PageRequest pageRequest=PageRequest.of(0,10);

    @Test
    // member dummy data insert test
    public void insertMembers() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Member member = Member.builder()
                    .email("member"+i+"@email.com")
                    .password(passwordEncoder.encode("1234"+i))
                    .birth(LocalDate.now())
                    .phone("010-0000-0000")
                    .name("name"+i)
                    .build();

            if (i<=10) member.addMemberRole(Role.MEMBER);
            else if (i <= 19) member.addMemberRole(Role.BUSINESSPERSON);
            else if (i == 20) member.addMemberRole(Role.ADMIN);

            memberRepository.save(member);
        });
    }

    @Test
    // email로 member 찾기 test
    public void findEmailTest() {
        Optional<Member> result = memberRepository.findByEmail("mh98@email.com");
        if (result.isEmpty()){
            log.info("not found member");
        } else {
            Member member = result.get();
            log.info("MNO: " + member.getMno());
        }
    }

    @Test
    public void findEmailWhereFromSolcialFalseTest() {
        Optional<Member> result = memberRepository.findByEmailAndFromSocialIsFalse("mmk275@naver.com");
        if(result.isEmpty()){
            log.info("not found");
        } else {
            log.info(result.get().getMno());
        }
    }

    @Test
    // member+phone로 member 찾기 test
    public void findMemberbyNamePhoneTest() {
        Optional<Member> result = memberRepository.findMemberByNameAndPhone("희범", "010-1234-5678");
        if (result.isEmpty()){
            log.info("not found member");
        } else {
            Member member = result.get();
            log.info("email: "+member.getEmail());
        }

    }

    @Test
    // email 중복 test
    public void existEmailTest(){
        log.info("is Duplicate?: "+memberRepository.existsByEmail("mmk2751@gmail.com"));
    }

    // 회원 프로필 조회 테스트
    @Test
    public void showProfileTest(){
        Object[] result = memberRepository.findProfileByMno(22L).get(0);
        for(Object profile : result){
            log.info(profile);
        }
    }

    // 회원 검색 테스트
//    @Test
//    public void searchUserTest(){
//        Object[] result = memberRepository.searchUser("", null,pageRequest).get();
//        for(Object user : result){
//            log.info(user);
//        }
//    }

    // 회원 프로필 조회에 팔로우 추가 테스트
    @Test
    void showFollowTest(){
        log.info("팔로워 : " + memberRepository.showFollowers(2L));
        log.info("팔로워 : " + memberRepository.showFollowersByName("이해창"));
        log.info("팔로잉 : " + memberRepository.showFollowings(2L));
        log.info("팔로워 : " + memberRepository.showFollowingsByName("이해창"));
    }

    // 회원정보에 role, social 여부 추가 테스트
    @Test
    void userInfoWithRoleTest() {
        Object[] result = memberRepository.userInfo(22L).get(0);
        for (Object user : result) {
            log.info(user);

            PageRequest pageRequest = PageRequest.of(0, 10);
            Page<Object[]> userlist = memberRepository.searchUser("", null, pageRequest);
            for (Object[] list : userlist) {
                log.info("user mno : " + list[0]);
                log.info("user image : " + list[1]);
                log.info("user name : " + list[2]);
                log.info("user 팔로잉 : " + list[3]);

            }
        }

    }
//    // 회원가입 대기 조회 테스트
//    @Test
//    public void showTest(){
//        PageRequest pageRequest1=PageRequest.of(0,10);
//        log.info(memberRepository.showJoinWaiting(pageRequest1));
//    }
//
//    // 회원가입 승인 테스트
//    @Test
//    public void joinTest(){
//        memberRepository.joinMember(8L);
//    }
//
//    // 회원탈퇴 테스트
//    @Test
//    void removeMemberTest(){
//        memberRepository.deleteById(17L);
//    }
//
//    //관리자 회원 검색
//    PageRequest pageRequest=PageRequest.of(0,10);
//    @Test
//    @Transactional
//    void searchAll(){
//        log.info("모두 검색 : "+memberRepository.searchMemberAll("",pageRequest));
//        log.info("사업자 검색 : "+memberRepository.searchBusiness("",pageRequest));
//        log.info("일반 유저 검색 : "+memberRepository.searchNomal("",pageRequest));
//        log.info("정지 유저 검색 : "+memberRepository.searchDisciplinary("",pageRequest));
//    }
//
//    // 프로필에 cart 추가 테스트
//    @Test
//    void showCart(){
//        log.info("카트" + memberRepository.showCart(2L));
//    }
//
//    // 유저프로필 검색 테스트
//    @Test
//    void findUserProfileTest(){
//        log.info("profile : " + memberRepository.findProfileByMno(2L));
//    }
//
//    // 프로필 이미지 업데이트 테스트
//    @Test
//    void memberImageUpdateTest(){
//        memberRepository.updateMemberImage("testImageSrc", 2L);
//    }
}