package service.dto.doctor;

import entity.DoctorSpeciality;
import entity.Qualification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.Dto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DoctorDto implements Dto {

    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private Qualification qualification;

    private DoctorSpeciality speciality;

}
