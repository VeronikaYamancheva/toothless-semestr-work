package ru.itis.vhsroni.appointment.service;

import ru.itis.vhsroni.appointment.data.dto.request.CreateAppointmentRequest;
import ru.itis.vhsroni.appointment.data.dto.request.UpdateAppointmentFormRequest;
import ru.itis.vhsroni.appointment.data.dto.response.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    UUID createNewAppointment(UUID userId, CreateAppointmentRequest createAppointmentRequest);

    List<ClientAppointmentResponse> getClientPastAppointments(UUID userId);

    List<ClientAppointmentResponse> getClientUpcomingAppointments(UUID userId);

    List<DentistAppointmentResponse> getDentistPastAppointments(UUID userId);

    List<DentistAppointmentResponse> getDentistUpcomingAppointments(UUID userId);

    List<AdminAppointmentShortResponse> getAppointmentsByDateRange(LocalDate beginDate, LocalDate endDate);

    AdminAppointmentDetailedResponse getAppointmentDetailedInfoForAdmin(UUID appointmentId);
}
