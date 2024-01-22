package com.dot.tour_info_service_server.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ReplyServiceImplTest {

    @Autowired
    private ReplyService replyService;

    @Test
    void showReplyList() {
       log.info(replyService.showReplyList(4L));
    }
}