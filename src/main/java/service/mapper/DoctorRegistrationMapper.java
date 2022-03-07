package service.mapper;

import service.dto.admin.DoctorRegistrationDto;
import entity.Doctor;

public class DoctorRegistrationMapper implements Mapper<DoctorRegistrationDto, Doctor> {

    @Override
    public Doctor mapFrom(DoctorRegistrationDto dto) {
        return Doctor.builder()
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
