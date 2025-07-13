package ru.itis.vhsroni.dentist.data.mapper;

import org.springframework.stereotype.Component;
import ru.itis.vhsroni.dentist.data.dto.response.DentistDetailedResponse;
import ru.itis.vhsroni.dentist.data.dto.response.DentistShortResponse;
import ru.itis.vhsroni.dentist.data.entity.Dentist;
import ru.itis.vhsroni.profile.data.dto.inner.DentistProfileInfo;

@Component
public class DentistMapper {

    public DentistShortResponse toShortResponse(Dentist dentist) {
        return DentistShortResponse.builder()
                .dentistId(dentist.getId())
                .userId(dentist.getUser().getUserId())
                .firstName(dentist.getUser().getFirstName())
                .lastName(dentist.getUser().getLastName())
                .middleName(dentist.getUser().getMiddleName())
                .specialization(dentist.getSpecialization().getName())
                .build();
    }


    public DentistDetailedResponse toDetailedResponse(Dentist dentist) {
        return DentistDetailedResponse.builder()
                .dentistId(dentist.getId())
                .userId(dentist.getUser().getUserId())
                .firstName(dentist.getUser().getFirstName())
                .lastName(dentist.getUser().getLastName())
                .middleName(dentist.getUser().getMiddleName())
                .email(dentist.getUser().getEmail())
                .birthDate(dentist.getUser().getBirthDate())
                .specialization(dentist.getSpecialization().getName())
                .experience(dentist.getExperience())
                .education(dentist.getEducation())
                .about(dentist.getAbout())
                .build();
    }

    public DentistProfileInfo toDentistProfileInfo(Dentist dentist) {
        return DentistProfileInfo.builder()
                .specialization(dentist.getSpecialization().getName())
                .education(dentist.getEducation())
                .experience(dentist.getExperience())
                .about(dentist.getAbout())
                .build();
    }
}
