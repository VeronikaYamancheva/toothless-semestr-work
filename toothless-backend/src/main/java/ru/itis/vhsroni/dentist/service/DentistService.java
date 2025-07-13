package ru.itis.vhsroni.dentist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.vhsroni.dentist.data.dto.response.DentistDetailedResponse;
import ru.itis.vhsroni.dentist.data.dto.response.DentistShortResponse;
import ru.itis.vhsroni.dentist.data.dto.response.DentistSpecializationResponse;
import ru.itis.vhsroni.profile.data.dto.inner.DentistProfileInfo;
import ru.itis.vhsroni.profile.data.dto.request.UpdateDentistProfileInfoRequest;

import java.util.List;
import java.util.UUID;

public interface DentistService {
    DentistProfileInfo getDentistProfileInfoByUserId(UUID userId);

    void updateDentistProfileInfo (UUID userId, UpdateDentistProfileInfoRequest updateDentistProfileInfoRequest);

    List<DentistSpecializationResponse> getAllSpecializations();

    Page<DentistShortResponse> getAllDentistsPage(Pageable pageable);

    DentistDetailedResponse getDentistById(UUID dentistId);

    List<DentistShortResponse> getDentistsDemo(int count);

    List<DentistShortResponse> getDentistsByProcedureId(UUID procedureId);

    void createNewDentistByUserId(UUID userId);
}
