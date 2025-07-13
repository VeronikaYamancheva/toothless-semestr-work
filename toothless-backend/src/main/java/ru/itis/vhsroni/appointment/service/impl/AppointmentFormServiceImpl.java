package ru.itis.vhsroni.appointment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.appointment.data.dto.request.UpdateAppointmentFormRequest;
import ru.itis.vhsroni.appointment.data.dto.response.AppointmentFormResponse;
import ru.itis.vhsroni.appointment.data.entity.AppointmentForm;
import ru.itis.vhsroni.appointment.data.mapper.AppointmentFormMapper;
import ru.itis.vhsroni.appointment.repository.impl.AppointmentFormJpaRepository;
import ru.itis.vhsroni.appointment.service.AppointmentFormService;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.NotFoundException;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentFormServiceImpl implements AppointmentFormService {

    private final AppointmentFormJpaRepository appointmentFormRepository;

    private final AppointmentFormMapper appointmentFormMapper;

    private final ErrorMessageProperties errorMessage;

    @Override
    public AppointmentFormResponse getAppointmentForm(UUID appointmentId) {
        AppointmentForm appointmentForm = appointmentFormRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new NotFoundException(errorMessage.appointmentNotFound(), ErrorCode.APPOINTMENT_NOT_FOUND));
        return appointmentFormMapper.toResponse(appointmentId, appointmentForm);
    }

    @Override
    public void updateAppointmentForm(UUID appointmentId, UpdateAppointmentFormRequest updateAppointmentFormRequest) {
        AppointmentForm appointmentForm = appointmentFormRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new NotFoundException(errorMessage.appointmentNotFound(), ErrorCode.APPOINTMENT_NOT_FOUND));
        appointmentForm.setComplaints(updateAppointmentFormRequest.complaints());
        appointmentForm.setSymptoms(updateAppointmentFormRequest.symptoms());
        appointmentForm.setDiagnosis(updateAppointmentFormRequest.diagnosis());
        appointmentForm.setProceduresDone(updateAppointmentFormRequest.proceduresDone());
        appointmentForm.setRecommendations(updateAppointmentFormRequest.recommendations());
        appointmentForm.setTreatmentPlan(updateAppointmentFormRequest.treatmentPlan());
        appointmentFormRepository.save(appointmentForm);
    }
}
