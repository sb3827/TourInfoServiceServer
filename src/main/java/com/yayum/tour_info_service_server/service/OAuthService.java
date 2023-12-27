package com.yayum.tour_info_service_server.service;


import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthService {
    Long socialMember(OAuth2User oAuth2User);
    void socialLogin(String code, String registrationId);
}
