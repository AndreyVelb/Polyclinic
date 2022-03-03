package entity.olddb;

import entity.DoctorSpeciality;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorOldDB implements BaseEntityOldDB<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "speciality")
    @Enumerated(EnumType.STRING)
    private DoctorSpeciality speciality;

}
