package service.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.doctor.DoctorDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DocAppForPatientDto {

    Long id;

    DoctorDto doctorDto;

    LocalDateTime dateAndTime;
}
