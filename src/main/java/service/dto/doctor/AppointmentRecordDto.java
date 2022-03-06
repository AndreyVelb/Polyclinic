package service.dto.doctor;

import entity.Doctor;
import entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import service.dto.Dto;
import service.dto.patient.PatientDto;
import servlet.converter.serializer.LocalDateSerializer;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentRecordDto implements Dto {

    Long id;

    PatientDto patientDto;

    DoctorDto doctorDto;

    @JsonSerialize(using = LocalDateSerializer.class)
    LocalDate visitDate;

    String healthComplaints;

    String doctorsRecommendation;

}
