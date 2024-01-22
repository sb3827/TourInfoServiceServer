package com.dot.tour_info_service_server.controller;

import com.dot.tour_info_service_server.dto.*;
import com.dot.tour_info_service_server.security.util.SecurityUtil;
import com.dot.tour_info_service_server.service.AuthService;
import com.dot.tour_info_service_server.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    // security context 의 principal 정보 가져오기
    // todo delete
    @GetMapping("/getTest")
    public ResponseEntity<String> getTest() {
        log.info(SecurityUtil.getCurrentMemberEmail());
        log.info(SecurityUtil.getCurrentMemberMno());
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

    // permit all
    @PostMapping("/login")
    public ResponseEntity<ResponseMapDTO> login(@RequestBody JwtRequestDTO requestDTO) {
        ResponseMapDTO result;
        try {
            Long mno = authService.login(requestDTO);
            String refreshToken = tokenService.generateRefreshToken(mno);
            TokenDTO tokens = TokenDTO.builder()
                    .refreshToken(refreshToken)
                    .token(tokenService.createNewAccessToken(refreshToken))
                    .build();
            result = ResponseMapDTO.builder()
                    .response(Map.of("mno", mno, "tokens", tokens))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // permit all
    @PostMapping("/newToken")
    public ResponseEntity<Object> createNewToken(@RequestBody RefreshDTO refreshDTO) {
        Map<String, String> result = new HashMap<>();
        try {
            String newToken = tokenService.createNewAccessToken(refreshDTO.getRefreshToken());
            result.put("msg", "로그인 성공");
            result.put("token", newToken);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .msg(e.getMessage())
                    .result(false)
                    .build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // authenticated
    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            authService.logout();
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            return new ResponseEntity<>(new ResponseDTO("success", true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //permit all
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Long>> register(@RequestBody SignupRequestDTO signupDTO) {
        Map<String, Long> responseMap = new HashMap<>();

        try {
            Long num = authService.signup(signupDTO);
            responseMap.put("mno", num);
        } catch (MailException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    // permit all
    // 이메일 중복 검사
    @PostMapping("/email/check")
    public ResponseEntity<Map<String, Boolean>> checkDuplicate(@RequestBody MemberDTO memberDTO) {
        Map<String, Boolean> responseMap = new HashMap<>();
        Boolean isDuplicate = authService.emailCheck(memberDTO.getEmail());

        responseMap.put("isDuplicate", isDuplicate);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    // permit all
    // 이메일 인증 재전송
    @PostMapping("/email/re-validation")
    public ResponseEntity<Map<String, String>> checkValidate(@RequestBody EmailDTO emailDTO) {
        try {
            authService.resendEmail(emailDTO.getEmail());
        } catch (Exception e) {
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("msg", e.getMessage());
            log.error(responseMap.get("msg"));
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // 이메일 검증 주소
    // permit all
    @GetMapping("/email/validation")
    public ModelAndView checkValtidate(@RequestParam("email") String email, @RequestParam("token") String token) throws BadRequestException {
        boolean isValid = authService.checkValidate(email);

        try {
            tokenService.validationToken(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadRequestException("잘못된 요청");
        }

        if (isValid) {
            // 성공한 경우
            return new ModelAndView(new RedirectView("http://localhost:3000/login"));
        } else {
            // 실패한 경우 (badRequest)
            throw new BadRequestException("잘못된 요청");
//            return new ModelAndView("errorPage"); // 실패 시 보여줄 에러 페이지로 이동
        }
    }

    // 이메일 찾기
    // permit all
    @PostMapping("/email/find")
    public ResponseEntity<Map<String, String>> findMail(@RequestBody MemberDTO memberDTO) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            String email = authService.findEmail(memberDTO.getName(), memberDTO.getPhone());
            responseMap.put("email", email);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    // 비밀번호 변경
    // todo 변경
    @PostMapping("password/change")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody ChangeMemberDTO changeMemberDTO) {
        if (SecurityUtil.validateEmail(changeMemberDTO.getEmail())) {
            ResponseDTO result = authService.changePassword(changeMemberDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 비밀번호 찾기
    // permit all
    @PostMapping("password/lost")
    public ResponseEntity<ResponseDTO> lostPassword(@RequestBody MemberDTO memberDTO) {
        ResponseDTO result = authService.resetPassword(memberDTO);
        HttpStatus tmp = HttpStatus.OK;

        return new ResponseEntity<>(result, tmp);
    }
}
