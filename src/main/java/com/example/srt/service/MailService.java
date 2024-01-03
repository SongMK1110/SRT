package com.example.srt.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.example.srt.common.RedisUtil;
import com.example.srt.dto.user.request.VerifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    RedisUtil redisUtil;

    private String ePw;

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);// 보내는 대상
        message.setSubject("SRT 회원가입 이메일 인증");// 제목
        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> SRT 입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>SRT 이용해주셔서 감사합니다<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("이메일 주소", "SRT 관리자"));// 보내는 사람

        return message;
    }

    // 랜덤 인증 코드 전송
    public String createKey() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    key.append((char) ((rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

    public int sendSimpleMessage(String to) throws Exception {
        ePw = createKey();
        MimeMessage message = createMessage(to);
        try {
            // redisUtil.setDataExpire(ePw, to, (long) 30);
            emailSender.send(message);
        } catch (MailException es) {
            throw new IllegalArgumentException();
        }
        return 1;
    }

    public String verify() {
        return ePw;
    }
}
