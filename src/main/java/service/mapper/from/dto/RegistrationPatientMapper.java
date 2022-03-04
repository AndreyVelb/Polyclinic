package service.mapper.from.dto;

import service.dto.patient.PatientRegistrationDto;
import entity.Patient;
import service.mapper.Mapper;

public class RegistrationPatientMapper implements Mapper<PatientRegistrationDto, Patient> {

    @Override
    public Patient mapFrom(PatientRegistrationDto dto) {
        return Patient.builder()
                .lastName(dto.getLastName())
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .birthDate(dto.getBirthDate())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .build();
    }
}
