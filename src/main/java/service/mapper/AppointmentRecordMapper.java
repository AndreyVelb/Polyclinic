package service.mapper;

import service.dto.doctor.AppointmentRecordSavedDto;
import entity.AppointmentRecord;

public class AppointmentRecordMapper implements Mapper<AppointmentRecordSavedDto, AppointmentRecord> {

    @Override
    public AppointmentRecord mapFrom(AppointmentRecordSavedDto dto) {
        return AppointmentRecord.builder()
                .patient(dto.getPatient())
                .doctor(dto.getDoctor())
                .visitDate(dto.getVisitDate())
                .healthComplaints(dto.getHealthComplaints())
                .doctorsRecommendation(dto.getDoctorsRecommendation())
                .build();
    }
}
