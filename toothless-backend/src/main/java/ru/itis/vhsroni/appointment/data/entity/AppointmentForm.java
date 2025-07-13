package ru.itis.vhsroni.appointment.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.itis.vhsroni.procedure.data.entity.Procedure;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment_form")
public class AppointmentForm {

    @Id
    @UuidGenerator
    @Column(name = "form_id", nullable = false)
    private UUID formId;

    @ManyToOne
    @JoinColumn(name = "expected_procedure_id")
    private Procedure expectedProcedure;

    @Column(name = "complaints", columnDefinition = "TEXT")
    private String complaints;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "procedures_done", columnDefinition = "TEXT")
    private String proceduresDone;

    @Column(name = "recommendations", columnDefinition = "TEXT")
    private String recommendations;

    @Column(name = "treatment_plan", columnDefinition = "TEXT")
    private String treatmentPlan;

    @OneToOne(mappedBy = "appointmentForm")
    private Appointment appointment;
}

