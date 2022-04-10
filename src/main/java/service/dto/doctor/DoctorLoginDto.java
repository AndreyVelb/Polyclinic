package service.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.Dto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorLoginDto implements Dto {

    private String login;

    private String password;
}
