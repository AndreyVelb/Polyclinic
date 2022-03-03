package service.dto.doctor;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorLoginDto {

    String login;

    String password;
}
