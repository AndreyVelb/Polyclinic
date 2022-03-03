package service.mapper.from.dto;

import service.dto.patient.PatientRegistrationDto;
import entity.newdb.PatientNewDB;
import service.mapper.Mapper;

public class RegistrationPatientMapper implements Mapper<PatientRegistrationDto, PatientNewDB> {

    @Override
    public PatientNewDB mapFrom(PatientRegistrationDto dto) {
        return PatientNewDB.builder()
                .lastName(dto.getLastName())
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .birthDate(dto.getBirthDate())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .build();
    }
}
