package ru.itis.vhsroni.dentist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.config.property.AppConfigProperties;
import ru.itis.vhsroni.dentist.api.DentistApi;
import ru.itis.vhsroni.dentist.data.dto.response.DentistDetailedResponse;
import ru.itis.vhsroni.dentist.data.dto.response.DentistShortResponse;
import ru.itis.vhsroni.dentist.data.dto.response.DentistSpecializationResponse;
import ru.itis.vhsroni.dentist.service.DentistService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DentistController implements DentistApi {

    private final DentistService dentistService;

    private final AppConfigProperties appConfigProperties;

    @Override
    public Page<DentistShortResponse> getAllDentist(Pageable pageable) {
        return dentistService.getAllDentistsPage(pageable);
    }

    @Override
    public DentistDetailedResponse getDentistById(UUID dentistId) {
        return dentistService.getDentistById(dentistId);
    }

    @Override
    public List<DentistShortResponse> getDentistsDemoForMainPage() {
        return dentistService.getDentistsDemo(appConfigProperties.dentistsDemoCount());
    }

    @Override
    public List<DentistShortResponse> getDentistsByProcedureId(UUID procedureId) {
        return dentistService.getDentistsByProcedureId(procedureId);
    }

    @Override
    public List<DentistSpecializationResponse> getAllSpecializations() {
        return dentistService.getAllSpecializations();
    }
}
