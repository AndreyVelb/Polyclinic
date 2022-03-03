package entity.olddb;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patient")
@Data
@EqualsAndHashCode(exclude = "receptionResults")
@ToString(exclude = "receptionResults")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientOldDB implements BaseEntityOldDB<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppointmentRecordOldDB> receptionResults;
}
