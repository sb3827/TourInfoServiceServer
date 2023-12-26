package com.yayum.tour_info_service_server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.entity.Role;
import com.yayum.tour_info_service_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
    private final Environment env;
    private final MemberRepository memberRepository;

    // no use
    public Long socialMember(OAuth2User oAuth2User) {
        Member member = Member.builder()
                .email(oAuth2User.getAttribute("email"))
                .image(oAuth2User.getAttribute("picture"))
                .name(oAuth2User.getAttribute("name"))
                .password("1")
                .isApprove(true)
                .isValidate(true)
                .isReset(false)
                .fromSocial(true)
                .build();
        member.addMemberRole(Role.MEMBER);

        Optional<Member> result = memberRepository.findByEmail(member.getEmail());

        if (result.isEmpty()) {
            member = memberRepository.save(member);
            return member.getMno();
        } else {
            return result.get().getMno();
        }

    }


    // no use
    private final RestTemplate restTemplate = new RestTemplate();

    //no use, 정보 수신 가능, 정보 처리 미완
    @Override
    public void socialLogin(String code, String registrationId) {
        log.info("code= " + code);
        log.info("registrationId= " + registrationId);
        String accessToken = getAccessToken(code, registrationId);
        log.info("accessToken= " + accessToken);
        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
        log.info("userResourceNode= " + userResourceNode);

        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String name = userResourceNode.get("name").asText();
        log.info("id= " + id);
        log.info("email= " + email);
        log.info("nickname= " + name);

        // repository 확인
        // 로그인 or 회원가입
        // 토큰 발급
    }

    //no use
    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("spring.security.oauth2.client.registration." + registrationId + ".client-id");
        String clientSecret = env.getProperty("spring.security.oauth2.client.registration." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("spring.security.oauth2.client.registration." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty("spring.security.oauth2.provider." + registrationId + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> reponseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = reponseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    //no use
    private JsonNode getUserResource(String accessToken, String registrationId) {
        String resourceUri = env.getProperty("spring.security.oauth2.provider." + registrationId + ".user-info-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}
