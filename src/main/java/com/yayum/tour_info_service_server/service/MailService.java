package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.entity.Member;

public interface MailService {
    void sendEmailToMember(String email, String title, String text);

    void sendPassword(String email, String name, String password);
}
