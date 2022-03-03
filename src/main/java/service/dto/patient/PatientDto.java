package service.dto.patient;

import lombok.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import servlet.converter.serializer.LocalDateSerializer;
import service.dto.Dto;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PatientDto implements Dto {

    Long id;

    String lastName;

    String firstName;

    String middleName;

    @JsonSerialize(using = LocalDateSerializer.class)
    LocalDate birthDate;

    String login;
}
