package entity.newdb;

import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = "patientsRecords")
@ToString(exclude = "patientsRecords")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "patients")
public class PatientNewDB implements BaseEntityNewDB<Long> {

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

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password")
    @ColumnTransformer(write = "crypt(?, gen_salt('bf'))")
    private String password;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppointmentRecordNewDB> patientsRecords;
}
