package service.dto;

import entity.Doctor;
import entity.Patient;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentRecordDto {

    Patient patient;

    Doctor doctor;

    LocalDate visitDate;

    String healthComplaints;

    String doctorsRecommendation;


}
