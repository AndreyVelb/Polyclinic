package service.dto.doctor;

import entity.Doctor;
import entity.Patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.Dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentRecordSavedDto implements Dto {

    private Patient patient;

    private Doctor doctor;

    private LocalDate visitDate;

    private String healthComplaints;

    private String doctorsRecommendation;
}
