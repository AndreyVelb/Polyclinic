package service.mapper.from.dto;

import service.dto.AppointmentRecordDto;
import entity.newdb.AppointmentRecordNewDB;
import service.mapper.Mapper;

public class AppointmentRecordMapper implements Mapper<AppointmentRecordDto, AppointmentRecordNewDB> {

    @Override
    public AppointmentRecordNewDB mapFrom(AppointmentRecordDto dto) {
        return AppointmentRecordNewDB.builder()
                .patient(dto.getPatient())
                .doctor(dto.getDoctor())
                .visitDate(dto.getVisitDate())
                .healthComplaints(dto.getHealthComplaints())
                .doctorsRecommendation(dto.getDoctorsRecommendation())
                .build();
    }
}
