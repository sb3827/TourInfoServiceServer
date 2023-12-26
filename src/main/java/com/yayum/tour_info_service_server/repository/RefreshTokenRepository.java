package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(Long userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Modifying
    @Transactional
    void deleteByUserId(Long mno);
}
