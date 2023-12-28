package com.yayum.tour_info_service_server.security.handler;

import com.yayum.tour_info_service_server.dto.TokenDTO;
import com.yayum.tour_info_service_server.security.dto.AuthMemberDTO;
import com.yayum.tour_info_service_server.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("success");
        AuthMemberDTO authMemberDTO = (AuthMemberDTO) authentication.getPrincipal();

        log.info("OAuth2User in principal=" + /*oAuth2User*/ authMemberDTO);
        log.info("generate token");
        TokenDTO token;
        try {
            // generate access token, refresh token
            token = tokenService.generateTokens(authMemberDTO.getMno());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // redirect http://localhost:8080/oauth/login
        String targetUrl = UriComponentsBuilder.fromUriString("/oauth/login")
                .queryParam("token", token.getToken())
                .queryParam("refreshToken", token.getRefreshToken())
                .build()
                .toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
