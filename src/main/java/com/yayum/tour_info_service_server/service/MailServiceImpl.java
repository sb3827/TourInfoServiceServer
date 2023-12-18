package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmailToMember(String email, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            //받는사람
            message.setTo(email);
            //제목
            message.setSubject(title);
            //내용
            message.setText(text);

            javaMailSender.send(message);

        } catch (MailException e) {
            // 메일 전송이 실패하면 예외가 발생
            e.printStackTrace();
        }
    }

    @Override
    public void sendPassword(String email, String name, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        String title = name + "님의 yayum 임시 비밀번호 발급안내";
        String text = name + "님의 임시 비밀번호 발급 안내 입니다.\n" +
                "임시 비밀번호 " + password + " 로 발급되었습니다.";
        try {
            //받는사람
            message.setTo(email);
            //제목
            message.setSubject(title);
            //내용
            message.setText(text);

            javaMailSender.send(message);

        } catch (MailException e) {
            // 메일 전송이 실패하면 예외가 발생
            e.printStackTrace();
            throw e;
        }
    }
}
