package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.security.util.SecurityUtil;
import com.yayum.tour_info_service_server.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@RequestMapping("auth") // 주의 properties의 context-path=/ 임
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // security context 의 principal 정보 가져오기
    // todo delete
    @GetMapping("/getTest")
    public ResponseEntity<String> getTest() {
        log.info(SecurityUtil.getCurrentMemberEmail());
        log.info(SecurityUtil.getCurrentMemberMno());
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody JwtRequestDTO requestDTO) {
        Map<String, String> result = new HashMap<>();
        try {
            String token = authService.login(requestDTO);
            result.put("msg", "로그인 성공");
            result.put("token", token);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.put("msg", e.getMessage());

            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseDTO logout(HttpServletRequest request, HttpServletResponse response) {
        // todo refresh token 생성시 refresh token 삭제 logic add
        try {
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            return new ResponseDTO("success", true);
        } catch (Exception e) {
            return new ResponseDTO(e.getMessage(), false);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Long>> register(@RequestBody SignupRequestDTO signupDTO) {
        Map<String, Long> responseMap = new HashMap<>();

        try {
            Long num = authService.signup(signupDTO);
            responseMap.put("mno", num);
        } catch (MailException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/email/check")
    public ResponseEntity<Map<String, Boolean>> checkDuplicate(@RequestBody MemberDTO memberDTO) {
        Map<String, Boolean> responseMap = new HashMap<>();
        Boolean isDuplicate = authService.emailCheck(memberDTO.getEmail());

        responseMap.put("isDuplicate", isDuplicate);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/email/validation")
    public void checkValidate() {

    }

    @GetMapping("/email/validation")
    public ModelAndView checkValtidate(@RequestParam("email") String email, @RequestParam("token") String token) {
        log.info("email: "+email);
        log.info("token: "+token);
        return new ModelAndView(new RedirectView("http://localhost:3000/login"));
    }

    @PostMapping("/email/find")
    public ResponseEntity<Map<String, String>> findMail(@RequestBody MemberDTO memberDTO) {
        Map<String, String> responseMap = new HashMap<>();
        String email = authService.findEmail(memberDTO.getName(), memberDTO.getPhone());

        responseMap.put("email", email);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("password/change")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody ChangeMemberDTO changeMemberDTO) {
        if (SecurityUtil.validateEmail(changeMemberDTO.getEmail())) {
            ResponseDTO result = authService.changePassword(changeMemberDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("password/lost")
    public ResponseEntity<ResponseDTO> lostPassword(@RequestBody MemberDTO memberDTO) {
        ResponseDTO result = authService.resetPassword(memberDTO);
        HttpStatus tmp = HttpStatus.OK;

        return new ResponseEntity<>(result, tmp);
    }
}
