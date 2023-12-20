package com.yayum.tour_info_service_server.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MailServiceTest {
    @Autowired
    MailService mailService;

    @Test
    // mail 전송 test
    public void sendMailTest() {
        mailService.sendEmailToMember("fldh3369@naver.com", "TEST", "TEST다 임마!");
    }

    @Test
    // 임시 비밀번호 전송 test
    public void sendPasswordTest() {
        mailService.sendPassword("mmk2751@gmail.com", "test", "test1234");
    }

    @Test
    public void sendValidateMailTest() {
        try {
            mailService.sendValidateUrl("mmk2751@gmail.com", "test", "test");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}