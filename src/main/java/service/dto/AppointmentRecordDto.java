package service.dto;

import entity.newdb.DoctorNewDB;
import entity.newdb.PatientNewDB;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentRecordDto {

    PatientNewDB patient;

    DoctorNewDB doctor;

    LocalDate visitDate;

    String healthComplaints;

    String doctorsRecommendation;


}
