package service.mapper;

import entity.DoctorsAppointment;
import lombok.RequiredArgsConstructor;
import service.dto.patient.DocAppForPatientDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class DocAppDtoForPatientMapper implements Mapper<DoctorsAppointment, DocAppForPatientDto>{
    private final DoctorDtoMapper doctorDtoMapper;

    @Override
    public DocAppForPatientDto mapFrom(DoctorsAppointment doctorsAppointment) {
        return DocAppForPatientDto.builder()
                .id(doctorsAppointment.getId())
                .doctorDto(doctorDtoMapper.mapFrom(doctorsAppointment.getDoctor()))
                .dateAndTime(LocalDateTime.parse(doctorsAppointment.getAppointmentDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .build();
    }

}
