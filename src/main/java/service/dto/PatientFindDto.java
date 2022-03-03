package service.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class PatientFindDto {

    String lastName;

    String firstName;

    String middleName;

    LocalDate birthday;
}
