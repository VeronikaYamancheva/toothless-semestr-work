package ru.itis.vhsroni.dentist.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.itis.vhsroni.procedure.data.entity.Procedure;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "specialization")
public class DentistSpecialization {

    @Id
    @UuidGenerator
    @Column(name = "specialization_id", columnDefinition = "UUID")
    private UUID specializationId;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @OneToMany(mappedBy = "specialization")
    private Set<Procedure> procedures;
}

