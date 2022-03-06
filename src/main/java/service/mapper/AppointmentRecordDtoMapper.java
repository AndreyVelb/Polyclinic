package service.mapper;

import entity.AppointmentRecord;
import entity.Doctor;
import service.dto.doctor.AppointmentRecordDto;
import service.dto.doctor.AppointmentRecordRequestDto;

public class AppointmentRecordDtoMapper implements Mapper<AppointmentRecord, AppointmentRecordDto>{
    DoctorDtoMapper doctorDtoMapper = new DoctorDtoMapper();
    PatientDtoMapper patientDtoMapper= new PatientDtoMapper();

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
