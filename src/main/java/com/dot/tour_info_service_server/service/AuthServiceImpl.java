package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.repository.MemberRepository;
import com.dot.tour_info_service_server.security.util.SecurityUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    @Override
    public Long login(JwtRequestDTO requestDTO) {
        Optional<Member> result = memberRepository.findByEmail(requestDTO.getEmail());

        if (result.isEmpty()) {
            throw new RuntimeException("유저 정보가 없습니다");
        }

        Member member = result.get();
        if (!passwordEncoder.matches(requestDTO.getPassword(), member.getPassword())) {
            throw new RuntimeException("Password가 일치하지 않습니다");
        }

        if (member.isReset()) {
            throw new RuntimeException("password 변경이 필요 합니다");
        }

        if (!member.isApprove()) {
            throw new RuntimeException("관리자의 승인이 필요합니다.");
        }

        if (!member.getIsValidate()) {
            throw new RuntimeException("이메일 인증이 필요합니다.");
        }

        return member.getMno();
    }

    @Override
    public Long signup(SignupRequestDTO signupDTO) {

        // email 중복처리
        if (memberRepository.existsByEmail(signupDTO.getEmail())) {
            throw new RuntimeException("중복된 이메일");
        }

        Member member = signupDtoToEntity(signupDTO);
        // password encoding
        member.changePassword(passwordEncoder.encode(member.getPassword()));
        member.changeIsValidate(false);
        try {
            member = memberRepository.save(member);
            TokenDTO token = tokenService.generateTokens(member.getMno());
            mailService.sendValidateUrl(signupDTO.getEmail(), signupDTO.getName(), token.getToken());
        } catch (MailException | MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new RuntimeException("메일 전송 오류");
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error(e.getMessage());
            throw new RuntimeException("토큰 생성 오류");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return member.getMno();
    }

    @Override
    public Boolean emailCheck(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public String findEmail(String name, String phone) {
        Optional<Member> result = memberRepository.findMemberByNameAndPhone(name, phone);

        // DB에 없는 경우
        if (result.isEmpty()) {
            throw new RuntimeException("not found member");
        }

        Member member = result.get();
        return member.getEmail();
    }

    @Override
    public ResponseDTO changePassword(ChangeMemberDTO changeMemberDTO) {
        Optional<Member> result = memberRepository.findByEmail(changeMemberDTO.getEmail());
        ResponseDTO responseDTO;

        // DB 없을 경우
        if (result.isEmpty()) {
            return ResponseDTO.builder()
                    .msg("not found member")
                    .result(false)
                    .build();
        }
        Member member = result.get();

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

    // 비밀번호 초기화
    @Override
    public ResponseDTO resetPassword(MemberDTO memberDTO) {
        Optional<Member> result = memberRepository.findByEmail(memberDTO.getEmail());

        // 검색결과x
        if (result.isEmpty()) {
            return ResponseDTO.builder()
                    .msg("not found member")
                    .result(false)
                    .build();
        }
        Member member = result.get();


        String oldPassword = member.getPassword();
        String password = generateRandomPassword();
        member.changeIsReset(); // 비밀번호 변경 요청 여부 변경
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

    private String generateRandomPassword() {
        final int pwLength = 12;
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[pwLength];
        secureRandom.nextBytes(randomBytes);

        String password = Base64.getEncoder().encodeToString(randomBytes);

        password = password.substring(0, pwLength);

        return password;
    }

    @Override
    public Boolean checkValidate(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);

        if (result.isEmpty()) {
            return false;
        }
        Member member = result.get();

        if (member.getIsValidate()){
            return false;
        }
        member.changeIsValidate(true);
        memberRepository.save(member);

        return true;
    }

    @Override
    public void logout() {
        Long mno = SecurityUtil.getCurrentMemberMno();
        tokenService.deleteRefreshToken(mno);
    }

    @Override
    public void resendEmail(String email) {
        Optional<Member> result = memberRepository.findByEmail(email);

        if (result.isEmpty()) {
            throw new RuntimeException("존재하지 않는 이메일");
        }

        try {
            Member member = result.get();
            if (member.getIsValidate()) {
                throw new RuntimeException("이미 인증된 이메일");
            }
            TokenDTO token = tokenService.generateTokens(member.getMno());
            mailService.reSendValidateUrl(member.getEmail(), member.getName(), token.getToken());
        } catch (MailException | MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new RuntimeException("메일 전송 오류");
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error(e.getMessage());
            throw new RuntimeException("토큰 생성 오류");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
