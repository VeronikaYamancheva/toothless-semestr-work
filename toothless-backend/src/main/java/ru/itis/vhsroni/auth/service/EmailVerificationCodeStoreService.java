package ru.itis.vhsroni.auth.service;

public interface EmailVerificationCodeStoreService {

    void storeEmailVerificationCode(String email, String verificationCode);

    String getEmailConfirmationCode(String email);

    boolean validateEmailConfirmationCode(String email, String code);

    void deleteEmailConfirmationCode(String email);
}
