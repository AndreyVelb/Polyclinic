package service.mapper.from.dto;

import entity.newdb.PatientNewDB;
import entity.olddb.PatientOldDB;
import service.mapper.Mapper;

import javax.persistence.ManyToOne;

public class PatientFromOldDBToNewDBMapper implements Mapper<PatientOldDB, PatientNewDB> {

    @Override
    public PatientNewDB mapFrom(PatientOldDB patientOldDB) {
        return PatientNewDB.builder()
                .lastName(patientOldDB.getLastName())
                .firstName(patientOldDB.getFirstName())
                .middleName(patientOldDB.getMiddleName())
                .birthDate(patientOldDB.getBirthDate())
                .build();
    }
}
