package com.yayum.tour_info_service_server.security.util;

import com.yayum.tour_info_service_server.security.dto.AuthMemberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtil {

    // logging을 위한 logger 객체 생성 -> log message 기록에 사용
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
    private SecurityUtil() {}

    // get email from principal in security context holder
    public static String getCurrentMemberEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }

        return authentication.getName();
    }
    // request email과 principal 검증
    public static boolean validateEmail(String email) {
        try {
            return Objects.equals(getCurrentMemberEmail(), email);
        } catch (Exception e) {
            return false;
        }
    }

    // get mno from principal in security context holder
    public static Long getCurrentMemberMno() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthMemberDTO principal = (AuthMemberDTO) authentication.getPrincipal();

        if(principal.getMno() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }

        return principal.getMno();
    }

    // request mno와 principal 검증
    public static boolean validateMno(Long mno) {
        try {
            return Objects.equals(getCurrentMemberMno(), mno);
        } catch (Exception e) {
            return false;
        }
    }
}
