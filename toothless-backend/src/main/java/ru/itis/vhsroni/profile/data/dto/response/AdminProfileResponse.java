package ru.itis.vhsroni.profile.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Administrator profile information", allOf = BaseProfileResponse.class)
public class AdminProfileResponse extends BaseProfileResponse {
}