package service.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.Dto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientLoginDto implements Dto {

    String login;

    String password;
}
