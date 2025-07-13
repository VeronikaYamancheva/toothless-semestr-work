package ru.itis.vhsroni.profile.data.dto.inner;

import lombok.Builder;

@Builder
public record ClientProfileInfo(
        String passportFullNumber,
        String passportIssuedBy,
        String passportIssueDate,
        String passportDivisionCode,
        String policyNumber,
        String snils
){
}
