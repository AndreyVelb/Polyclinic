package service.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.Dto;
import service.dto.doctor.DoctorDto;
import service.dto.patient.PatientDto;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DocAppForAdminDto implements Dto {

    private Long id;

    private DoctorDto doctorDto;

    private LocalDateTime dateAndTime;

    private PatientDto patientDto;
}
