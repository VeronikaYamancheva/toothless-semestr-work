package ru.itis.vhsroni.auth.data.dto.inner;

import lombok.Builder;

@Builder
public record JwtTokensResponse(
        String accessToken,
        String refreshToken
){
}
