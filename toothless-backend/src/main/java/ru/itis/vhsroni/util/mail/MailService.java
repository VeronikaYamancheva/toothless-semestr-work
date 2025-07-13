package ru.itis.vhsroni.util.mail;

public interface MailService {

    void sendEmailWithVerificationCode(String to, String code);
}
