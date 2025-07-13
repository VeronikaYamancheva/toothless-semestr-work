package ru.itis.vhsroni.procedure.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.dentist.data.entity.DentistSpecialization;
import ru.itis.vhsroni.dentist.repository.DentistSpecializationRepository;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.BadRequestException;
import ru.itis.vhsroni.error.exception.NotFoundException;
import ru.itis.vhsroni.procedure.data.dto.request.CreateProcedureRequest;
import ru.itis.vhsroni.procedure.data.dto.request.UpdateProcedureRequest;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureDetailedResponse;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureShortResponse;
import ru.itis.vhsroni.procedure.data.entity.Procedure;
import ru.itis.vhsroni.procedure.data.mapper.ProcedureMapper;
import ru.itis.vhsroni.procedure.repository.ProcedureRepository;
import ru.itis.vhsroni.procedure.service.ProcedureService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcedureServiceImpl implements ProcedureService {

    private final ProcedureRepository procedureRepository;

    private final ProcedureMapper procedureMapper;

    private final DentistSpecializationRepository dentistSpecializationRepository;

    private final ErrorMessageProperties errorMessage;

    @Override
    public Page<ProcedureShortResponse> getAllProceduresPage(Pageable pageable) {
        return procedureRepository.findAllByActiveTrue(pageable).map(procedureMapper::toShortResponse);
    }

    @Override
    public ProcedureDetailedResponse getProcedureById(UUID procedureId) {
        return procedureRepository.findById(procedureId)
                .map(procedureMapper::toDetailedResponse)
                .orElseThrow(() -> new NotFoundException(errorMessage.procedureNotFound(), ErrorCode.PROCEDURE_NOT_FOUND));
    }

    @Override
    public List<ProcedureShortResponse> getProceduresDemo(int count) {
        return procedureRepository.findLimitedProcedures(count).stream()
                .map(procedureMapper::toShortResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UUID createProcedure(CreateProcedureRequest createProcedureRequest) {
        DentistSpecialization specialization = dentistSpecializationRepository.findById(createProcedureRequest.specializationId()).orElseThrow(
                () -> new BadRequestException(errorMessage.specializationNotFound(), ErrorCode.SPECIALIZATION_NOT_FOUND)
        );
        Procedure procedure = Procedure.builder()
                .name(createProcedureRequest.name())
                .cost(createProcedureRequest.cost())
                .description(createProcedureRequest.description())
                .specialization(specialization)
                .active(true)
                .build();
        return procedureRepository.save(procedure).getProcedureId();
    }

    @Override
    public void updateProcedureById(UUID procedureId, UpdateProcedureRequest updateProcedureRequest) {
        DentistSpecialization specialization = dentistSpecializationRepository.findById(updateProcedureRequest.getSpecializationId()).orElseThrow(
                () -> new BadRequestException(errorMessage.specializationNotFound(), ErrorCode.SPECIALIZATION_NOT_FOUND));
        Procedure procedure = procedureRepository.findById(procedureId)
                .orElseThrow(() -> new BadRequestException(errorMessage.procedureNotFound(), ErrorCode.PROCEDURE_NOT_FOUND));
        procedure.setName(updateProcedureRequest.getName());
        procedure.setCost(updateProcedureRequest.getCost());
        procedure.setDescription(updateProcedureRequest.getDescription());
        procedure.setSpecialization(specialization);
        procedureRepository.save(procedure);
    }

    @Override
    public void deleteProcedureById(UUID procedureId) {
        Procedure procedure = procedureRepository.findById(procedureId)
                .orElseThrow(() -> new BadRequestException(errorMessage.procedureNotFound(), ErrorCode.PROCEDURE_NOT_FOUND));
        procedure.setActive(false);
        procedureRepository.save(procedure);
    }
}
