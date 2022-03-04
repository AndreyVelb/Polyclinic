package service.mapper.to;

import entity.Doctor;
import service.mapper.Mapper;
import service.dto.doctor.DoctorDto;

public class DoctorDtoMapper implements Mapper<Doctor, DoctorDto> {

    @Override
    public DoctorDto mapFrom(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .lastName(doctor.getLastName())
                .firstName(doctor.getFirstName())
                .middleName(doctor.getMiddleName())
                .login(doctor.getLogin())
                .qualification(doctor.getQualification())
                .speciality(doctor.getSpeciality())
                .build();
    }
}
