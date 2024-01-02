package com.dot.tour_info_service_server.controller;

import com.dot.tour_info_service_server.dto.TokenDTO;
import com.dot.tour_info_service_server.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("oauth")
@RequiredArgsConstructor
@Log4j2
public class OAuthController {
    private final OAuthService oAuthService;

    // security oauth2 logic
    //http://localhost:8080/oauth2/authorization/kakao // client kakao login
    //http://localhost:8080/oauth2/authorization/google // client google login
    //http://localhost:8080/oauth2/authorization/naver // client naver login

    // token return address
    @GetMapping("/login")
    public ResponseEntity<TokenDTO> login(TokenDTO tokenDTO){
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }


    // security의 oauth2가 아닌 직접 구현된 redirect controller // no use
    @GetMapping("/login/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId){
        log.info(code);
        log.info(registrationId);
        oAuthService.socialLogin(code, registrationId);

    }
}
