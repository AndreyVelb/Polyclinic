package service.mapper.from.dto;

import service.dto.DoctorRegistrationDto;
import entity.newdb.DoctorNewDB;
import service.mapper.Mapper;

public class DoctorRegistrationMapper implements Mapper<DoctorRegistrationDto, DoctorNewDB> {

    @Override
    public DoctorNewDB mapFrom(DoctorRegistrationDto dto) {
        return DoctorNewDB.builder()
                .lastName(dto.getLastName())
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .speciality(dto.getSpeciality())
                .qualification(dto.getQualification())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .build();
    }
}
