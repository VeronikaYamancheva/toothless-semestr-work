package ru.itis.vhsroni.util.mail.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.config.property.MailConfigProperties;
import ru.itis.vhsroni.util.mail.MailService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailConfigProperties mailConfig;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmailWithVerificationCode(String to, String code) {
        String text = mailConfig.verificationCodeText().concat(" ").concat(code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailConfig.senderUsername());
        message.setTo(to);
        message.setSubject(mailConfig.verificationCodeSubject());
        message.setText(text);
        javaMailSender.send(message);
        log.info("Email `{}` sended to {}", text, to);
    }
}
