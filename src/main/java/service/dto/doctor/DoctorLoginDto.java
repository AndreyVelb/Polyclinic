package service.dto.doctor;

import lombok.*;
import service.dto.Dto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorLoginDto implements Dto {

    String login;

    String password;
}
