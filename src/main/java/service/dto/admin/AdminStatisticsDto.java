package service.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.Dto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdminStatisticsDto implements Dto {

    int patientCount;

    int doctorCount;

}
