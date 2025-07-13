package ru.itis.vhsroni.profile.data.dto.inner;

import lombok.Builder;

@Builder
public record DentistProfileInfo(
    String specialization,
    Integer experience,
    String education,
    String about
){}
