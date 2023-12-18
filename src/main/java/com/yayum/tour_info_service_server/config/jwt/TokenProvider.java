package com.yayum.tour_info_service_server.config.jwt;

import com.yayum.tour_info_service_server.entity.Member;
import com.yayum.tour_info_service_server.security.dto.AuthMemberDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class TokenProvider {
    private final JwtProperties jwtProperties;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private Key key;

    public String generateToken(Member member, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }

    private String makeToken(Date expiry, Member member) {
        Date now = new Date();

        key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());

        return Jwts.builder()
                .header()
                .add("typ","JWT")
                .and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiration(expiry)
                .subject(member.getEmail())
                .claim(AUTHORITIES_KEY, member.getRoleSet())
                .claim("id", member.getMno())
                .signWith(key)
                .compact();
    }

    public boolean validToken(String token) {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());

        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // security context principal 저장
        UserDetails principal = new AuthMemberDTO(claims.getSubject(), claims.get("id", Long.class), "", false, authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Long getMemberId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    public String getMemberEmail(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class);
    }

    private Claims getClaims(String token) {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());

        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
