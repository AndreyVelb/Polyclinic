package service.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PatientFindByLastNameDto {

    String lastName;

}
