package com.dot.tour_info_service_server.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MailService {
    void sendEmailToMember(String email, String title, String text);
    void sendPassword(String email, String name, String password);
    void sendValidateUrl(String email, String name, String token) throws MessagingException, UnsupportedEncodingException;
    void reSendValidateUrl(String email, String name, String token) throws MessagingException, UnsupportedEncodingException;
}