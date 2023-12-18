package com.yayum.tour_info_service_server.controller;

import com.yayum.tour_info_service_server.dto.*;
import com.yayum.tour_info_service_server.security.dto.AuthMemberDTO;
import com.yayum.tour_info_service_server.service.AuthService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("auth") // 주의 properties의 context-path=/ 임
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // security context test get mapping
    // todo delete
    @GetMapping("/getTest")
    public ResponseEntity<String> getTest(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        log.info(((AuthMemberDTO) authentication.getPrincipal()).getMno());
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
        try {
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            return new ResponseDTO("success", true);
        } catch (Exception e) {
            return new ResponseDTO(e.getMessage(), false);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Long>> register(@RequestBody SignupDTO signupDTO) {
        Map<String, Long> responseMap = new HashMap<>();
        Long num = authService.signup(signupDTO);

        responseMap.put("mno", num);

        if (num >0) {
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/email/check")
    public ResponseEntity<Map<String, Boolean>> checkDuplicate(@RequestBody MemberDTO memberDTO){
//        log.info(memberDTO.getEmail());
        Map<String, Boolean> responseMap = new HashMap<>();
        Boolean isDuplicate = authService.emailCheck(memberDTO.getEmail());

        responseMap.put("isDuplicate", isDuplicate);

        return new ResponseEntity(responseMap, HttpStatus.OK);
    }

    @PostMapping("/email/validation")
    public void checkValidate(){

    }

    @PostMapping("/email/find")
    public ResponseEntity<Map<String, String>> findMail(@RequestBody MemberDTO memberDTO){
        Map<String, String> responseMap = new HashMap<>();
        String email = authService.findEmail(memberDTO.getName(), memberDTO.getPhone());

        responseMap.put("email", email);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("password/change")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody ChangeMemberDTO changeMemberDTO) {
        ResponseDTO result = authService.changePassword(changeMemberDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("password/lost")
    public ResponseEntity<ResponseDTO> lostPassword(@RequestBody MemberDTO memberDTO) {
        ResponseDTO result = authService.resetPassword(memberDTO);
        HttpStatus tmp = HttpStatus.OK;

        return new ResponseEntity<>(result, tmp);
    }
}
