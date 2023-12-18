package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.config.jwt.TokenProvider;
import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private MailService mailService;
    private final TokenProvider tockenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public String login(JwtRequestDTO requestDTO) {
        Optional<Member> result = memberRepository.findByEmail(requestDTO.getEmail());

        if (!result.isPresent()){
            throw new RuntimeException("유저 정보가 없습니다");
        }

        Member member = result.get();
        if (!passwordEncoder.matches(requestDTO.getPassword(), member.getPassword())) {
            throw new RuntimeException("Password가 일치하지 않습니다");
        }

        String token = tockenProvider.generateToken(member, Duration.ofMinutes(10));
        //jwt token 생성 -> token return

        return token;
    }

    @Override
    public Long signup(SignupDTO signupDTO) {
        //todo 빈값 처리
        // todo email 중복처리
        Member member = dtoToEntity(signupDTO);
        member.changePassword(passwordEncoder.encode(member.getPassword()));
        try {
            memberRepository.save(member);
        } catch (Exception e) {
            log.error(e.getClass());
            return (long) -1;
        }

        return member.getMno();
    }

    @Override
    public Boolean emailCheck(String email) {
        //Member member = memberRepository.findMemberByEmail(email);

        //todo test
        return memberRepository.existsByEmail(email);

//        if (member == null) {
//            return false;
//        }
//        if (member.getMno() > 0) {
//            return true;
//        }
//        return false;
    }

    @Override
    public String findEmail(String name, String phone) {
        Member member = memberRepository.findMemberByNameAndPhone(name, phone);

        if (member != null) {
            return member.getEmail();
        } else {
            return null;
        }
    }

    @Override
    public ResponseDTO changePassword(ChangeMemberDTO changeMemberDTO) {
        Member member = memberRepository.findMemberByEmail(changeMemberDTO.getEmail());
        ResponseDTO responseDTO;

        // DB 없을 경우
        if (member == null){
            return ResponseDTO.builder()
                    .msg("not found member")
                    .result(false)
                    .build();
        }

        // 기존 비밀번호가 일치하지 않을 경우
        if (!passwordEncoder.matches(changeMemberDTO.getOldPassword(), member.getPassword())) {
            return ResponseDTO.builder()
                    .msg("password mismatch")
                    .result(false)
                    .build();
        }

        String newPassword = passwordEncoder.encode(changeMemberDTO.getNewPassword());
        member.changePassword(newPassword);
        try {
            memberRepository.save(member);
            responseDTO = ResponseDTO.builder()
                    .msg("change success")
                    .result(true)
                    .build();

        } catch (Exception e) {
            responseDTO = ResponseDTO.builder()
                    .msg("change failed")
                    .result(false)
                    .build();
        }

        return responseDTO;
    }

    @Override
    public ResponseDTO resetPassword(MemberDTO memberDTO) {
        // todo add column isReset in member entity
        Member member = memberRepository.findMemberByEmail(memberDTO.getEmail());

        if (member == null){
            return ResponseDTO.builder()
                    .msg("not found member")
                    .result(false)
                    .build();
        }

        String oldPassword = member.getPassword();
        String password = generateRandomPassword();
        member.changePassword(passwordEncoder.encode(password));
        try {
            memberRepository.save(member);
            mailService.sendPassword(member.getEmail(), member.getName(), password);
        } catch (Exception e) {
            log.error(e.getClass());
            if (e instanceof MailSendException) {
                member.changePassword(oldPassword);
                memberRepository.save(member);
                return ResponseDTO.builder()
                        .msg("email transfer failed")
                        .result(false)
                        .build();
            } else {
                return ResponseDTO.builder()
                        .msg("change failed")
                        .result(false)
                        .build();
            }
        }

        return ResponseDTO.builder()
                .msg("success")
                .result(true)
                .build();
    }

    private String generateRandomPassword(){
        final int pwLength = 12;
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[pwLength];
        secureRandom.nextBytes(randomBytes);

        String password = Base64.getEncoder().encodeToString(randomBytes);

        password = password.substring(0, pwLength);

        return password;
    }
}
