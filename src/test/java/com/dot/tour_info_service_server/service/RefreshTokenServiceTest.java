package com.dot.tour_info_service_server.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class RefreshTokenServiceTest {
    @Autowired
    RefreshTokenService refreshTokenService;

    @Test
    public void deleteRefreshTest(){
        refreshTokenService.deleteRefreshToken(6L);
    }

}