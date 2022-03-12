package service.mapper;

import entity.DoctorsAppointment;
import lombok.RequiredArgsConstructor;
import service.dto.admin.DoctorsAppointmentForAdminDto;

@RequiredArgsConstructor
public class DoctorsAppointmentDtoMapper implements Mapper<DoctorsAppointment, DoctorsAppointmentForAdminDto>{
    private final DoctorDtoMapper doctorDtoMapper;
    private final PatientDtoMapper patientDtoMapper;

    @Override
    public DoctorsAppointmentForAdminDto mapFrom(DoctorsAppointment doctorsAppointment) {
        return DoctorsAppointmentForAdminDto.builder()
                .id(doctorsAppointment.getId())
                .doctorDto(doctorDtoMapper.mapFrom(doctorsAppointment.getDoctor()))
                .patientDto(patientDtoMapper.mapFrom(doctorsAppointment.getPatient()))
                .dateAndTime(doctorsAppointment.getAppointmentDateTime())
                .build();
    }
}
