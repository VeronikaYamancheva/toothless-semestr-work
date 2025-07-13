package ru.itis.vhsroni.client.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.util.converter.EncryptConverter;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @UuidGenerator
    @Column(name = "client_id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserData user;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "passport_series_number")
    private String passportFullNumber;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "passport_issued_by")
    private String passportIssuedBy;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "passport_issue_date")
    private String passportIssueDate;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "passport_division_code")
    private String passportDivisionCode;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "policy_number")
    private String policyNumber;

    @Convert(converter = EncryptConverter.class)
    @Column(name = "snils")
    private String snils;
}

