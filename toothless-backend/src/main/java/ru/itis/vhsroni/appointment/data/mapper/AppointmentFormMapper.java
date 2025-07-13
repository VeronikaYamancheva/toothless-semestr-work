package ru.itis.vhsroni.appointment.data.mapper;

import org.springframework.stereotype.Component;
import ru.itis.vhsroni.appointment.data.dto.response.AppointmentFormResponse;
import ru.itis.vhsroni.appointment.data.entity.AppointmentForm;

import java.util.UUID;

@Component
public class AppointmentFormMapper {

    public AppointmentFormResponse toResponse(UUID appointmentId, AppointmentForm appointmentForm) {
        return AppointmentFormResponse.builder()
                .appointmentId(appointmentId)
                .expectedProcedure(appointmentForm.getExpectedProcedure().getName())
                .complaints(appointmentForm.getComplaints())
                .symptoms(appointmentForm.getSymptoms())
                .diagnosis(appointmentForm.getDiagnosis())
                .proceduresDone(appointmentForm.getProceduresDone())
                .recommendations(appointmentForm.getRecommendations())
                .treatmentPlan(appointmentForm.getTreatmentPlan())
                .build();
    }
}
