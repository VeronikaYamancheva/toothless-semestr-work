package ru.itis.vhsroni.procedure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.config.property.AppConfigProperties;
import ru.itis.vhsroni.error.dto.OperationResponse;
import ru.itis.vhsroni.procedure.api.ProcedureApi;
import ru.itis.vhsroni.procedure.data.dto.request.CreateProcedureRequest;
import ru.itis.vhsroni.procedure.data.dto.request.UpdateProcedureRequest;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureCreationResponse;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureDetailedResponse;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureShortResponse;
import ru.itis.vhsroni.procedure.service.ProcedureService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProcedureController implements ProcedureApi {

    private final ProcedureService procedureService;

    private final AppConfigProperties appConfig;

    @Override
    public Page<ProcedureShortResponse> getAllProcedures(Pageable pageable) {
        return procedureService.getAllProceduresPage(pageable);
    }

    @Override
    public ProcedureDetailedResponse getProcedureById(UUID procedureId) {
        return procedureService.getProcedureById(procedureId);
    }

    @Override
    public List<ProcedureShortResponse> getProceduresDemoForMainPage() {
        return procedureService.getProceduresDemo(appConfig.proceduresDemoCount());
    }

    @Override
    public ProcedureCreationResponse createProcedure(CreateProcedureRequest createProcedureRequest) {
        UUID procedureId = procedureService.createProcedure(createProcedureRequest);
        return ProcedureCreationResponse.builder().procedureId(procedureId).isSuccess(true).build();
    }

    @Override
    public OperationResponse updateProcedure(UUID procedureId, UpdateProcedureRequest updateProcedureRequest) {
        procedureService.updateProcedureById(procedureId, updateProcedureRequest);
        return OperationResponse.builder().isSuccess(true).build();
    }

    @Override
    public OperationResponse deleteProcedure(UUID procedureId) {
        procedureService.deleteProcedureById(procedureId);
        return OperationResponse.builder().isSuccess(true).build();
    }
}
