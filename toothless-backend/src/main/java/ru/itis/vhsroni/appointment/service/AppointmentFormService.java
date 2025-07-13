package ru.itis.vhsroni.appointment.service;

import ru.itis.vhsroni.appointment.data.dto.request.UpdateAppointmentFormRequest;
import ru.itis.vhsroni.appointment.data.dto.response.AppointmentFormResponse;

import java.util.UUID;

public interface AppointmentFormService {

    AppointmentFormResponse getAppointmentForm(UUID appointmentId);

    void updateAppointmentForm(UUID appointmentId, UpdateAppointmentFormRequest updateAppointmentFormRequest);
}
