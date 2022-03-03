package service.dto.patient;

import lombok.*;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import servlet.converter.deserializer.LocalDateDeserializer;
import service.dto.Dto;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientRegistrationDto implements Dto {

    String lastName;

    String firstName;

    String middleName;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate birthDate;

    String login;

    String password;
}
