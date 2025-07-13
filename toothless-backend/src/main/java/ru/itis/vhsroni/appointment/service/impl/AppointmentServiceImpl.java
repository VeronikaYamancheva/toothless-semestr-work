package ru.itis.vhsroni.appointment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.appointment.data.dto.request.CreateAppointmentRequest;
import ru.itis.vhsroni.appointment.data.dto.response.*;
import ru.itis.vhsroni.appointment.data.entity.Appointment;
import ru.itis.vhsroni.appointment.data.entity.AppointmentForm;
import ru.itis.vhsroni.appointment.data.mapper.AppointmentMapper;
import ru.itis.vhsroni.appointment.repository.AppointmentCriteriaBuilderRepository;
import ru.itis.vhsroni.appointment.repository.AppointmentRepository;
import ru.itis.vhsroni.appointment.repository.impl.AppointmentFormJpaRepository;
import ru.itis.vhsroni.appointment.service.AppointmentService;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.repository.UserDataRepository;
import ru.itis.vhsroni.config.property.AppConfigProperties;
import ru.itis.vhsroni.dentist.data.entity.Dentist;
import ru.itis.vhsroni.dentist.repository.DentistRepository;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.BadRequestException;
import ru.itis.vhsroni.error.exception.InternalException;
import ru.itis.vhsroni.procedure.data.entity.Procedure;
import ru.itis.vhsroni.procedure.repository.ProcedureRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DentistRepository dentistRepository;

    private final AppConfigProperties appConfig;

    private final ProcedureRepository procedureRepository;

    private final AppointmentMapper appointmentMapper;

    private final UserDataRepository userDataRepository;

    private final AppointmentFormJpaRepository appointmentFormRepository;

    private final AppointmentCriteriaBuilderRepository appointmentCriteriaBuilderRepository;

    private final ErrorMessageProperties errorMessage;

    @Override
    public UUID createNewAppointment(UUID userId, CreateAppointmentRequest createAppointmentRequest) {
        UUID dentistId = createAppointmentRequest.dentistId();
        LocalDate appointmentDate = createAppointmentRequest.date();
        LocalTime beginTime = createAppointmentRequest.beginTime();
        LocalTime endTime = createAppointmentRequest.endTime();
        log.debug("Create app with dentist {}, app-date {}, app-begin-time, app-end-time{}", dentistId, appointmentDate, beginTime, endTime);
        List<Appointment> sameAppointments = appointmentRepository.findAllByDentist_IdAndDateAndBeginTimeAndEndTime(
                dentistId, appointmentDate, beginTime, endTime);
        if (!sameAppointments.isEmpty()) {
            throw new BadRequestException(errorMessage.appointmentHasAlreadyBooked(), ErrorCode.APPOINTMENT_HAS_ALREADY_BOOKED);
        }
        log.debug("There were no doctor with id: {} appointments {} from {} to {}", dentistId, appointmentDate, beginTime, endTime);
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        Dentist dentist = dentistRepository.findById(dentistId)
                .orElseThrow(() -> new BadRequestException(errorMessage.dentistNotFound(), ErrorCode.DENTIST_NOT_FOUND));
        List<Appointment> clientAppointments = appointmentRepository.findAllByClient_UserIdAndDateAndBeginTimeAndEndTime(
                userData.getUserId(), appointmentDate, beginTime, endTime);
        if (!clientAppointments.isEmpty()) {
            throw new BadRequestException(errorMessage.clientAlreadyHasAppointment(), ErrorCode.CLIENT_ALREADY_HAS_APPOINTMENT);
        }
        if (appointmentDate.isBefore(LocalDate.now())) {
            throw new BadRequestException(errorMessage.pastDate(), ErrorCode.PAST_DATE);
        }
        if (appointmentDate.equals(LocalDate.now())) {
            if (beginTime.isBefore(LocalTime.now())) {
                throw new BadRequestException(errorMessage.pastTime(), ErrorCode.PAST_TIME);
            }
        }
        if (!Duration.between(beginTime, endTime).equals(appConfig.appointmentDuration())) {
            throw new BadRequestException(errorMessage.incorrectTimeSlot(), ErrorCode.INCORRECT_TIME_SLOT);
        }
        LocalTime lunchBeginTime = appConfig.lunchBeginTime();
        LocalTime lunchEndTime = appConfig.lunchEndTime();
        if (beginTime.equals(lunchBeginTime) || endTime.equals(lunchEndTime) || (beginTime.isAfter(lunchBeginTime) && endTime.isBefore(lunchEndTime))) {
            throw new BadRequestException(errorMessage.lunchTime(), ErrorCode.LUNCH_TIME);
        }
        AppointmentForm form = new AppointmentForm();
        if (createAppointmentRequest.procedureId() != null) {
            Procedure procedure = procedureRepository.findById(createAppointmentRequest.procedureId())
                    .orElseThrow(() -> new BadRequestException(errorMessage.procedureNotFound(), ErrorCode.PROCEDURE_NOT_FOUND));
            form = AppointmentForm.builder()
                    .expectedProcedure(procedure)
                    .build();
        }
        form = appointmentFormRepository.save(form);
        log.debug("Appointment form successfully created");
        Appointment appointment = appointmentRepository.save(Appointment.builder()
                .client(userData)
                .dentist(dentist)
                .beginTime(beginTime)
                .endTime(endTime)
                .date(createAppointmentRequest.date())
                .appointmentForm(form)
                .build());
        log.debug("Successful appointment creation");
        return appointment.getAppointmentId();
    }

    @Override
    public List<ClientAppointmentResponse> getClientPastAppointments(UUID userId) {
        return appointmentRepository.findAllPastByClientAndDate(userId, LocalDate.now()).stream()
                .map(appointmentMapper::toClientResponse)
                .toList();
    }

    @Override
    public List<ClientAppointmentResponse> getClientUpcomingAppointments(UUID userId) {
        return appointmentRepository.findAllUpcomingByClientAndDate(userId, LocalDate.now()).stream()
                .map(appointmentMapper::toClientResponse)
                .toList();
    }

    @Override
    public List<AdminAppointmentShortResponse> getAppointmentsByDateRange(LocalDate beginDate, LocalDate endDate) {
        return appointmentCriteriaBuilderRepository.findAppointmentsByDateRange(beginDate, endDate).stream()
                .map(appointmentMapper::toAdminAppointmentShortResponse)
                .toList();
    }

    @Override
    public AdminAppointmentDetailedResponse getAppointmentDetailedInfoForAdmin(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new BadRequestException(errorMessage.appointmentNotFound(), ErrorCode.APPOINTMENT_NOT_FOUND));
        return appointmentMapper.toAdminAppointmentDetailedResponse(appointment);
    }

    @Override
    public List<DentistAppointmentResponse> getDentistPastAppointments(UUID userId) {
        Dentist dentist = dentistRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        List<Appointment> appointments = appointmentRepository.findAllPastByDentistAndDate(dentist.getId(), LocalDate.now());
        return appointments.stream()
                .map(appointmentMapper::toDentistAppointmentResponse)
                .toList();
    }

    @Override
    public List<DentistAppointmentResponse> getDentistUpcomingAppointments(UUID userId) {
        Dentist dentist = dentistRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        List<Appointment> appointments = appointmentRepository.findAllUpcomingByDentistAndDate(dentist.getId(), LocalDate.now());
        return appointments.stream()
                .map(appointmentMapper::toDentistAppointmentResponse)
                .toList();
    }
}
