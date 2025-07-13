package ru.itis.vhsroni.dentist.data.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.itis.vhsroni.auth.data.entity.UserData;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dentist")
public class Dentist {

    @Id
    @Column(name = "dentist_id", columnDefinition = "UUID")
    @UuidGenerator
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserData user;

    @ManyToOne
    @JoinColumn(name = "specialization")
    private DentistSpecialization specialization;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "education")
    private String education;

    @Column(name = "about", columnDefinition = "TEXT")
    private String about;
}

