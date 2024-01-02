package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.entity.Role;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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

}