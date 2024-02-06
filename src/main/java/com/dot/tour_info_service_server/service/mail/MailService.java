package com.dot.tour_info_service_server.service.mail;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MailService {
    void sendPassword(String email, String name, String password) throws MessagingException, UnsupportedEncodingException;
    void sendValidateUrl(String email, String name, String token) throws MessagingException, UnsupportedEncodingException;
    void reSendValidateUrl(String email, String name, String token) throws MessagingException, UnsupportedEncodingException;
}
