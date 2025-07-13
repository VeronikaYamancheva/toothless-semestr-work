package ru.itis.vhsroni.procedure.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.itis.vhsroni.dentist.data.entity.DentistSpecialization;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "procedure")
public class Procedure {

    @Id
    @UuidGenerator
    @Column(name = "procedure_id", columnDefinition = "UUID")
    private UUID procedureId;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost", nullable = false)
    private int cost;

    @Column(name = "is_active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private DentistSpecialization specialization;
}
