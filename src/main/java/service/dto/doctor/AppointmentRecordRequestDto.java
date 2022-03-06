package service.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.Dto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentRecordRequestDto implements Dto {

    String healthComplaints;

    String doctorsRecommendation;
}
