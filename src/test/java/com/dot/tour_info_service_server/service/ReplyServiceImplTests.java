package com.dot.tour_info_service_server.service;

import com.dot.tour_info_service_server.service.reply.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class ReplyServiceImplTests {

    @Autowired
    private ReplyService replyService;

    @Test
    void showParentReplyList() {
        log.info(replyService.parentReply(1L));
    }

    @Test
    void showChildReplyList() {
        log.info(replyService.childReply(1L,1L));
    }

    @Test
    void showReplyList() {
       log.info(replyService.showReplyList(4L));
    }

    @Test
    void replyByMember() {
        log.info(replyService.getListOfReplyByMember(1L));
    }

}