package service.mapper;

import entity.Patient;
import service.mapper.Mapper;
import service.dto.patient.PatientDto;

public class PatientDtoMapper implements Mapper<Patient, PatientDto> {

    @Override
    public PatientDto mapFrom(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .lastName(patient.getLastName())
                .firstName(patient.getFirstName())
                .middleName(patient.getMiddleName())
                .birthDate(patient.getBirthDate())
                .build();
    }
}
