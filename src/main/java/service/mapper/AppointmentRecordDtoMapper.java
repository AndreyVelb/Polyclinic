package service.mapper;

import entity.AppointmentRecord;
import entity.Doctor;
import lombok.RequiredArgsConstructor;
import service.dto.doctor.AppointmentRecordDto;
import service.dto.doctor.AppointmentRecordRequestDto;

@RequiredArgsConstructor
public class AppointmentRecordDtoMapper implements Mapper<AppointmentRecord, AppointmentRecordDto>{
    private final DoctorDtoMapper doctorDtoMapper;
    private final PatientDtoMapper patientDtoMapper;

    @Override
    public AppointmentRecordDto mapFrom(AppointmentRecord record) {

        return AppointmentRecordDto.builder()
                .id(record.getId())
                .patientDto(patientDtoMapper.mapFrom(record.getPatient()))
                .doctorDto(doctorDtoMapper.mapFrom(record.getDoctor()))
                .visitDate(record.getVisitDate())
                .healthComplaints(record.getHealthComplaints())
                .doctorsRecommendation(record.getDoctorsRecommendation())
                .build();
    }
}
