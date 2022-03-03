package service.mapper.to;

import entity.newdb.PatientNewDB;
import service.mapper.Mapper;
import service.dto.patient.PatientDto;

public class PatientDtoMapper implements Mapper<PatientNewDB, PatientDto> {

    @Override
    public PatientDto mapFrom(PatientNewDB patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .lastName(patient.getLastName())
                .firstName(patient.getFirstName())
                .middleName(patient.getMiddleName())
                .birthDate(patient.getBirthDate())
                .login(patient.getLogin())
                .build();
    }
}
