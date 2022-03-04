package service.mapper.from.dto;

import service.dto.AppointmentRecordDto;
import entity.AppointmentRecord;
import service.mapper.Mapper;

public class AppointmentRecordMapper implements Mapper<AppointmentRecordDto, AppointmentRecord> {

    @Override
    public AppointmentRecord mapFrom(AppointmentRecordDto dto) {
        return AppointmentRecord.builder()
                .patient(dto.getPatient())
                .doctor(dto.getDoctor())
                .visitDate(dto.getVisitDate())
                .healthComplaints(dto.getHealthComplaints())
                .doctorsRecommendation(dto.getDoctorsRecommendation())
                .build();
    }
}
