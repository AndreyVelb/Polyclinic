package service.mapper;

import entity.DoctorsAppointment;
import lombok.RequiredArgsConstructor;
import service.dto.admin.DocAppForAdminDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class DocAppDtoForAdminMapper implements Mapper<DoctorsAppointment, DocAppForAdminDto>{
    private final DoctorDtoMapper doctorDtoMapper;
    private final PatientDtoMapper patientDtoMapper;

    @Override
    public DocAppForAdminDto mapFrom(DoctorsAppointment doctorsAppointment) {
        return DocAppForAdminDto.builder()
                .id(doctorsAppointment.getId())
                .doctorDto(doctorDtoMapper.mapFrom(doctorsAppointment.getDoctor()))
                .patientDto(patientDtoMapper.mapFrom(doctorsAppointment.getPatient()))
                .dateAndTime(LocalDateTime.parse(doctorsAppointment.getAppointmentDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .build();
    }
}
