package ru.itis.vhsroni.dentist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.repository.UserDataRepository;
import ru.itis.vhsroni.dentist.data.dto.response.DentistDetailedResponse;
import ru.itis.vhsroni.dentist.data.dto.response.DentistShortResponse;
import ru.itis.vhsroni.dentist.data.dto.response.DentistSpecializationResponse;
import ru.itis.vhsroni.dentist.data.entity.Dentist;
import ru.itis.vhsroni.dentist.data.entity.DentistSpecialization;
import ru.itis.vhsroni.dentist.data.mapper.DentistMapper;
import ru.itis.vhsroni.dentist.repository.DentistRepository;
import ru.itis.vhsroni.dentist.repository.DentistSpecializationRepository;
import ru.itis.vhsroni.dentist.service.DentistService;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.BadRequestException;
import ru.itis.vhsroni.error.exception.InternalException;
import ru.itis.vhsroni.error.exception.NotFoundException;
import ru.itis.vhsroni.profile.data.dto.inner.DentistProfileInfo;
import ru.itis.vhsroni.profile.data.dto.request.UpdateDentistProfileInfoRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DentistServiceImpl implements DentistService {

    private final DentistRepository dentistRepository;

    private final UserDataRepository userDataRepository;

    private final DentistSpecializationRepository dentistSpecializationRepository;

    private final DentistMapper dentistMapper;

    private final ErrorMessageProperties errorMessage;

    @Override
    public DentistProfileInfo getDentistProfileInfoByUserId(UUID userId) {
        Dentist dentist = dentistRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        return dentistMapper.toDentistProfileInfo(dentist);
    }

    @Override
    public void updateDentistProfileInfo(UUID userId, UpdateDentistProfileInfoRequest updateDentistProfileInfoRequest) {
        Dentist dentist = dentistRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        dentist.setEducation(updateDentistProfileInfoRequest.education());
        dentist.setExperience(updateDentistProfileInfoRequest.experience());
        dentist.setAbout(updateDentistProfileInfoRequest.about());
        DentistSpecialization specialization = dentistSpecializationRepository.findById(updateDentistProfileInfoRequest.specializationId())
                .orElseThrow(() -> new BadRequestException(errorMessage.specializationNotFound(), ErrorCode.SPECIALIZATION_NOT_FOUND));
        dentist.setSpecialization(specialization);
        dentistRepository.save(dentist);
    }

    @Override
    public List<DentistSpecializationResponse> getAllSpecializations() {
        List<DentistSpecialization> specializations = dentistSpecializationRepository.findAll();
        return specializations.stream()
                .map(specialization -> DentistSpecializationResponse.builder()
                        .specializationId(specialization.getSpecializationId())
                        .name(specialization.getName())
                        .build())
                .toList();
    }

    @Override
    public Page<DentistShortResponse> getAllDentistsPage(Pageable pageable) {
        return dentistRepository.findAll(pageable).map(dentistMapper::toShortResponse);
    }

    @Override
    public DentistDetailedResponse getDentistById(UUID dentistId) {
        return dentistRepository.findById(dentistId)
                .map(dentistMapper::toDetailedResponse)
                .orElseThrow(() -> new NotFoundException(errorMessage.dentistNotFound(), ErrorCode.DENTIST_NOT_FOUND));
    }

    @Override
    public List<DentistShortResponse> getDentistsDemo(int count) {
        return dentistRepository.findLimitedDentists(count).stream()
                .map(dentistMapper::toShortResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DentistShortResponse> getDentistsByProcedureId(UUID procedureId) {
        return dentistRepository.findDentistsByProcedure(procedureId).stream()
                .map(dentistMapper::toShortResponse)
                .toList();
    }

    @Override
    public void createNewDentistByUserId(UUID userId) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new BadRequestException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        Dentist dentist = Dentist.builder().user(userData).build();
        dentistRepository.save(dentist);
    }
}
