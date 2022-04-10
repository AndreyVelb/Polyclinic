package service.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import service.dto.Dto;
import service.dto.patient.PatientDto;
import service.dto.serializer.LocalDateSerializer;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentRecordDto implements Dto {

    private Long id;

    private PatientDto patientDto;

    private DoctorDto doctorDto;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate visitDate;

    private String healthComplaints;

    private String doctorsRecommendation;

}
