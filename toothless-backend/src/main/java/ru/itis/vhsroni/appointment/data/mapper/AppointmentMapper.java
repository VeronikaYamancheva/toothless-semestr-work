package ru.itis.vhsroni.appointment.data.mapper;

import org.springframework.stereotype.Component;
import ru.itis.vhsroni.appointment.data.dto.response.AdminAppointmentDetailedResponse;
import ru.itis.vhsroni.appointment.data.dto.response.AdminAppointmentShortResponse;
import ru.itis.vhsroni.appointment.data.dto.response.ClientAppointmentResponse;
import ru.itis.vhsroni.appointment.data.dto.response.DentistAppointmentResponse;
import ru.itis.vhsroni.appointment.data.entity.Appointment;
import ru.itis.vhsroni.util.dto.UserFullName;

@Component
public class AppointmentMapper {

    public ClientAppointmentResponse toClientResponse(Appointment entity) {
        return ClientAppointmentResponse.builder()
                .appointmentId(entity.getAppointmentId())
                .dentist(UserFullName.builder()
                        .firstName(entity.getClient().getFirstName())
                        .lastName(entity.getClient().getLastName())
                        .middleName(entity.getClient().getMiddleName())
                        .build())
                .date(entity.getDate())
                .beginTime(entity.getBeginTime())
                .endTime(entity.getEndTime())
                .build();
    }

    public AdminAppointmentShortResponse toAdminAppointmentShortResponse(Appointment entity) {
        return AdminAppointmentShortResponse.builder()
                .appointmentId(entity.getAppointmentId())
                .dentist(UserFullName.builder()
                        .firstName(entity.getDentist().getUser().getFirstName())
                        .lastName(entity.getDentist().getUser().getLastName())
                        .middleName(entity.getDentist().getUser().getMiddleName())
                        .build())
                .client(UserFullName.builder()
                        .firstName(entity.getClient().getFirstName())
                        .lastName(entity.getClient().getLastName())
                        .middleName(entity.getClient().getMiddleName())
                        .build())
                .date(entity.getDate())
                .beginTime(entity.getBeginTime())
                .endTime(entity.getEndTime())
                .build();
    }

    public AdminAppointmentDetailedResponse toAdminAppointmentDetailedResponse(Appointment entity) {
        return AdminAppointmentDetailedResponse.builder()
                .appointmentId(entity.getAppointmentId())
                .dentist(AdminAppointmentDetailedResponse.DentistResponse.builder()
                        .firstName(entity.getDentist().getUser().getFirstName())
                        .lastName(entity.getDentist().getUser().getLastName())
                        .middleName(entity.getDentist().getUser().getMiddleName())
                        .email(entity.getDentist().getUser().getEmail())
                        .phoneNumber(entity.getDentist().getUser().getPhoneNumber())
                        .build())
                .client(AdminAppointmentDetailedResponse.ClientResponse.builder()
                        .firstName(entity.getClient().getFirstName())
                        .lastName(entity.getClient().getLastName())
                        .middleName(entity.getClient().getMiddleName())
                        .email(entity.getClient().getEmail())
                        .phoneNumber(entity.getClient().getPhoneNumber())
                        .birthDate(entity.getClient().getBirthDate())
                        .build())
                .date(entity.getDate())
                .beginTime(entity.getBeginTime())
                .endTime(entity.getEndTime())
                .build();
    }

    public DentistAppointmentResponse toDentistAppointmentResponse(Appointment entity) {
        return DentistAppointmentResponse.builder()
                .appointmentId(entity.getAppointmentId())
                .client(UserFullName.builder()
                        .firstName(entity.getClient().getFirstName())
                        .lastName(entity.getClient().getLastName())
                        .middleName(entity.getClient().getMiddleName())
                        .build())
                .date(entity.getDate())
                .beginTime(entity.getBeginTime())
                .endTime(entity.getEndTime())
                .build();
    }
}
