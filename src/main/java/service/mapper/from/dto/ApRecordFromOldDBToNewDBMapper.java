package service.mapper.from.dto;

import entity.newdb.AppointmentRecordNewDB;
import entity.newdb.DoctorNewDB;
import entity.newdb.PatientNewDB;
import entity.olddb.AppointmentRecordOldDB;
import lombok.RequiredArgsConstructor;
import service.mapper.Mapper;

public class ApRecordFromOldDBToNewDBMapper implements Mapper<AppointmentRecordOldDB, AppointmentRecordNewDB> {

    @Override
    public AppointmentRecordNewDB mapFrom(AppointmentRecordOldDB appointmentRecordOldDB) {
        this.mapFrom(appointmentRecordOldDB, null, null);
        return null;
    }

    public AppointmentRecordNewDB mapFrom(AppointmentRecordOldDB appointmentRecordOldDB, DoctorNewDB doctorNewDB, PatientNewDB patientNewDB) {
        return AppointmentRecordNewDB.builder()
                .doctor(doctorNewDB)
                .patient(patientNewDB)
                .visitDate(appointmentRecordOldDB.getReceptionDate())
                .doctorsRecommendation(appointmentRecordOldDB.getDoctorsRecommendation())
                .healthComplaints(appointmentRecordOldDB.getHealthComplaints())
                .build();
    }


}
