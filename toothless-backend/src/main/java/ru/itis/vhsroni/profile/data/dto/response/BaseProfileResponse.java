package ru.itis.vhsroni.profile.data.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.itis.vhsroni.auth.data.enums.AuthProvider;
import ru.itis.vhsroni.profile.data.dto.inner.UserCredentialsProfileInfo;
import ru.itis.vhsroni.profile.data.enums.ProfileType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@SuperBuilder
@NoArgsConstructor
@Schema(
        description = "Base profile information common for all user types",
        discriminatorProperty = "profileType",
        subTypes = {
                AdminProfileResponse.class,
                DentistProfileResponse.class,
                ClientProfileResponse.class
        }
)
public abstract class  BaseProfileResponse {
    @Schema(
            description = "Type of user profile (determines which additional fields are available)",
            example = "DENTIST"
    )
    protected ProfileType profileType;

    @Schema(
            description = "User's first name",
            example = "Ivan"
    )
    protected String firstName;

    @Schema(
            description = "User's last name",
            example = "Petrov"
    )
    protected String lastName;

    @Schema(
            description = "User's middle name (patronymic)",
            example = "Sergeevich"
    )
    protected String middleName;

    @Schema(
            description = "User's birth date in ISO format (YYYY-MM-DD)",
            example = "1985-05-15",
            type = "string",
            format = "date"
    )
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate birthDate;

    @Schema(
            description = "User's email address",
            example = "user@example.com",
            format = "email"
    )
    protected String email;

    @Schema(
            description = "User's phone number in Russian format",
            example = "+7 (123) 456-78-90"
    )
    protected String phoneNumber;

    @Schema(
            description = "Whether the profile is approved by administration",
            example = "true"
    )
    protected boolean isApproved;

    @Schema(description = "List of authentication providers linked to this account")
    protected List<UserCredentialsProfileInfo.AuthProviderResponse> authProviders;
}