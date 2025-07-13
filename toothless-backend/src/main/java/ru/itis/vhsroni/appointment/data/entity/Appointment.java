package ru.itis.vhsroni.appointment.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.dentist.data.entity.Dentist;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment")
public class Appointment {

    @Id
    @UuidGenerator
    @Column(name = "appointment_id", columnDefinition = "UUID")
    private UUID appointmentId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private UserData client;

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false)
    private Dentist dentist;

    @Column(name = "begin_time", nullable = false)
    private LocalTime beginTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "appointment_form_id")
    private AppointmentForm appointmentForm;
}

