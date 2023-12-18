package com.yayum.tour_info_service_server.config.jwt;

import com.yayum.tour_info_service_server.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
@Log4j2
class TockenProviderTest {
    @Autowired
    TokenProvider tockenProvider;
    @Test
    public void gentokenTest(){
        log.info(tockenProvider.generateToken(Member.builder()
                        .mno(1L)
                        .email("email@email.com")
                .build(), Duration.ofMinutes(10)));
    }

    @Test
    public void validTokenTest() {
        log.info(tockenProvider.validToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJtbWsyNzUxQGdtYWlsLmNvbSIsImlhdCI6MTcwMjU0MzU4NywiZXhwIjoxNzAyNTQzNTg4LCJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpZCI6MX0.VWXJ0pWfaqezePk7-bqDUb0bQAqAtVMm5lreepyV3vlmm_CxoST0VvC5IXSzuuKl"));
    }

    @Test
    public void getMemberIdTest() {
        log.info(tockenProvider.getMemberId("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJtbWsyNzUxQGdtYWlsLmNvbSIsImlhdCI6MTcwMjU0NjQxNSwiZXhwIjoxNzAyNTQ3MDE1LCJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoxfQ.sWS8why1RSXRR6ByDa8pEOPYjymWVzZQSGswEsZaD2DBgUGvJIqyl1n6EpQb9Kkx"));
    }

    @Test
    public void getMemberEmailTest() {
        log.info(tockenProvider.getMemberEmail("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJtbWsyNzUxQGdtYWlsLmNvbSIsImlhdCI6MTcwMjU0NjQxNSwiZXhwIjoxNzAyNTQ3MDE1LCJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoxfQ.sWS8why1RSXRR6ByDa8pEOPYjymWVzZQSGswEsZaD2DBgUGvJIqyl1n6EpQb9Kkx"));
    }
}