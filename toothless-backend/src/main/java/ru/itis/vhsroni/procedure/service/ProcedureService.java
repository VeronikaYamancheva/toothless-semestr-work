package ru.itis.vhsroni.procedure.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.vhsroni.procedure.data.dto.request.CreateProcedureRequest;
import ru.itis.vhsroni.procedure.data.dto.request.UpdateProcedureRequest;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureDetailedResponse;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureShortResponse;

import java.util.List;
import java.util.UUID;

public interface ProcedureService {

    Page<ProcedureShortResponse> getAllProceduresPage(Pageable pageable);

    ProcedureDetailedResponse getProcedureById(UUID procedureId);

    List<ProcedureShortResponse> getProceduresDemo(int count);

    UUID createProcedure(CreateProcedureRequest createProcedureRequest);

    void updateProcedureById(UUID procedureId, UpdateProcedureRequest updateProcedureRequest);

    void deleteProcedureById(UUID procedureId);
}
