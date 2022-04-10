package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "doctors")
public class Doctor implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "qualification")
    @Enumerated(EnumType.STRING)
    private Qualification qualification;

    @Column(name = "speciality")
    @Enumerated(EnumType.STRING)
    private DoctorSpeciality speciality;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password")
    @ColumnTransformer(write = "crypt(?, gen_salt('bf'))")
    private String password;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<AppointmentRecord> recordsWrittenByDoctor;

}
