package service.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.Dto;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PatientLoginDto implements Dto {

    private String login;

    private String password;
}
