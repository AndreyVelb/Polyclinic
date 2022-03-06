package service.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PatientFindDto {

    String lastName;

    String firstName;

    String middleName;

    LocalDate birthday;
}
