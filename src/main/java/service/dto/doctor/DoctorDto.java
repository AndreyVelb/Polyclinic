package service.dto.doctor;

import entity.DoctorSpeciality;
import entity.Qualification;
import lombok.*;
import service.dto.Dto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DoctorDto implements Dto {

    Long id;

    String lastName;

    String firstName;

    String middleName;

    Qualification qualification;

    DoctorSpeciality speciality;

}
