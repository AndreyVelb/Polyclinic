package service.dto.patient;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import service.dto.serializer.LocalDateSerializer;
import service.dto.Dto;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PatientDto implements Dto {

    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;
}
