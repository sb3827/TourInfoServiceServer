package com.dot.tour_info_service_server.security.handler;

import com.dot.tour_info_service_server.dto.TokenDTO;
import com.dot.tour_info_service_server.entity.Disciplinary;
import com.dot.tour_info_service_server.entity.Member;
import com.dot.tour_info_service_server.repository.DisciplinaryRepository;
import com.dot.tour_info_service_server.repository.MemberRepository;
import com.dot.tour_info_service_server.security.dto.AuthMemberDTO;
import com.dot.tour_info_service_server.service.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final DisciplinaryRepository disciplinaryRepository;
    private final MemberRepository memberRepository;

    @Value("${client.address}")
    private String clientAddress;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        AuthMemberDTO authMemberDTO = (AuthMemberDTO) authentication.getPrincipal();

        String targetUrl;

        Optional<Member> result = memberRepository.findById(authMemberDTO.getMno());
        Member member = result.get();
        Disciplinary disciplinary = disciplinaryRepository.reportList(member.getMno());
        if (member.getDisciplinary() >= 5 || (disciplinary != null && disciplinary.getExpDate().isAfter(LocalDateTime.now()))) {
            String expDate = disciplinary.getExpDate() == null ? "무기한 " : disciplinary.getExpDate().toString();

            targetUrl = UriComponentsBuilder
                    .fromHttpUrl(("localhost".equals(clientAddress) ? "http://localhost:3000/oauth2" : clientAddress + "/oauth2"))
                    .queryParam("success", true)
                    .queryParam("reason", disciplinary.getReason())
                    .queryParam("expdate", expDate)
                    .build()
                    .toUriString();
        } else {
            TokenDTO token;
            try {
                // generate access token, refresh token
                token = tokenService.generateTokens(authMemberDTO.getMno());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            targetUrl = UriComponentsBuilder
                    .fromHttpUrl(("localhost".equals(clientAddress) ? "http://localhost:3000/oauth2" : clientAddress + "/oauth2"))
                    .queryParam("success", true)
                    .queryParam("mno", authMemberDTO.getMno())
                    .queryParam("token", token.getToken())
                    .queryParam("refreshToken", token.getRefreshToken())
                    .build()
                    .toUriString();
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
