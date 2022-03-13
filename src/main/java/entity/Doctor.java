package entity;


import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = {"recordsWrittenByDoctor"})
@ToString(exclude = {"recordsWrittenByDoctor"})
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

//    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, optional = false)
//    private WorkSchedule schedule;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppointmentRecord> recordsWrittenByDoctor;

//    public void setSchedule(WorkSchedule schedule){
//        schedule.setDoctor(this);
//        this.schedule = schedule;
//    }

}
