package ru.itis.vhsroni.appointment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.appointment.api.AppointmentApi;
import ru.itis.vhsroni.appointment.data.dto.request.CreateAppointmentRequest;
import ru.itis.vhsroni.appointment.data.dto.request.UpdateAppointmentFormRequest;
import ru.itis.vhsroni.appointment.data.dto.response.*;
import ru.itis.vhsroni.appointment.service.AppointmentFormService;
import ru.itis.vhsroni.appointment.service.AppointmentService;
import ru.itis.vhsroni.auth.data.principal.UnifiedAuthPrincipal;
import ru.itis.vhsroni.error.dto.OperationResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppointmentController implements AppointmentApi {

    private final AppointmentService appointmentService;

    private final AppointmentFormService appointmentFormService;

    @Override
    public CreateAppointmentResponse createAppointment(UnifiedAuthPrincipal authPrincipal, CreateAppointmentRequest createAppointmentRequest) {
        UUID appointmentId = appointmentService.createNewAppointment(authPrincipal.getUserId(), createAppointmentRequest);
        return CreateAppointmentResponse.builder().appointmentId(appointmentId).isSuccess(true).build();
    }

    @Override
    public List<ClientAppointmentResponse> getClientPastAppointments(UnifiedAuthPrincipal authPrincipal) {
        return appointmentService.getClientPastAppointments(authPrincipal.getUserId());
    }

    @Override
    public List<ClientAppointmentResponse> getClientUpcomingAppointments(UnifiedAuthPrincipal authPrincipal) {
        return appointmentService.getClientUpcomingAppointments(authPrincipal.getUserId());
    }

    @Override
    public List<DentistAppointmentResponse> getDentistPastAppointments(UnifiedAuthPrincipal authPrincipal) {
        return appointmentService.getDentistPastAppointments(authPrincipal.getUserId());
    }

    @Override
    public List<DentistAppointmentResponse> getDentistUpcomingAppointments(UnifiedAuthPrincipal authPrincipal) {
        return appointmentService.getDentistUpcomingAppointments(authPrincipal.getUserId());
    }

    @Override
    public List<AdminAppointmentShortResponse> getAppointmentsByDateRange(LocalDate beginDate, LocalDate endDate) {
        return appointmentService.getAppointmentsByDateRange(beginDate, endDate);
    }

    @Override
    public AdminAppointmentDetailedResponse getAppointmentDetailedInfoForAdmin(UUID appointmentId) {
        return appointmentService.getAppointmentDetailedInfoForAdmin(appointmentId);
    }

    @Override
    public AppointmentFormResponse getAppointmentFormByAppointmentId(UUID appointmentId) {
        return appointmentFormService.getAppointmentForm(appointmentId);
    }

    @Override
    public OperationResponse updateAppointmentForm(UUID appointmentId, UpdateAppointmentFormRequest appointmentFormRequest) {
        appointmentFormService.updateAppointmentForm(appointmentId, appointmentFormRequest);
        return OperationResponse.builder().isSuccess(true).build();
    }
}