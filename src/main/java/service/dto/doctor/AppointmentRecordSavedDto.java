package service.dto.doctor;

import entity.Doctor;
import entity.Patient;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentRecordSavedDto {

    Patient patient;

    Doctor doctor;

    LocalDate visitDate;

    String healthComplaints;

    String doctorsRecommendation;
}
