package ru.itis.vhsroni.config.property;

public class ValidationConstants {

    public static final int FIRST_NAME_MIN_LENGTH = 2;

    public static final int FIRST_NAME_MAX_LENGTH = 50;

    public static final int LAST_NAME_MIN_LENGTH = 2;

    public static final int LAST_NAME_MAX_LENGTH = 50;

    public static final int MIDDLE_NAME_MIN_LENGTH = 2;

    public static final int MIDDLE_NAME_MAX_LENGTH = 50;

    public static final int RAW_PASSWORD_MIN_LENGTH = 8;

    public static final int RAW_PASSWORD_MAX_LENGTH = 30;

    public static final int BIRTHDATE_MIN_YEAR = 1905;

    public static final int EMAIL_MIN_LENGTH = 6;

    public static final int EMAIL_MAX_LENGTH = 60;

    public static final String PHONE_PATTERN = "^\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$";

    public static final int DENTIST_ACCESS_CODE_LENGTH = 6;

    public static final int COMMENT_CONTENT_MAX_LENGTH = 1000;

    public static final int PROCEDURE_NAME_MIN_LENGTH = 4;

    public static final int PROCEDURE_NAME_MAX_LENGTH = 64;

    public static final int PROCEDURE_DESCRIPTION_MIN_LENGTH = 4;

    public static final int PROCEDURE_DESCRIPTION_MAX_LENGTH = 255;

    public static final int PROCEDURE_MIN_COST = 10;

    public static final int PROCEDURE_MAX_COST = 5000;

    public static final int APPOINTMENT_FORM_TEXT_MAX_LENGTH = 2000;
}