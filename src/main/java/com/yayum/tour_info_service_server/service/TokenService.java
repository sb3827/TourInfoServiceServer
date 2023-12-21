package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.config.jwt.TokenProvider;
import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;

    public String createNewAccessToken(String refreshToken) throws IllegalAccessException {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalAccessException("Unexpected token");
        }

        Long UserId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        Optional<Member> result = memberRepository.findById(UserId);

        if (result.isEmpty()){
            throw new IllegalAccessException("Unexpected token");
        }
        return tokenProvider.generateToken(result.get(), Duration.ofMinutes(10));
    }

    public String generateRefreshToken(Long mno) {
        return tokenProvider.generateRefresh(mno);
    }
}
