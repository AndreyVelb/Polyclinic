package entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.*;
import org.hibernate.boot.model.source.spi.HibernateTypeSource;

import javax.persistence.*;
import javax.print.Doc;

@Entity
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "schedules")
public class WorkSchedule implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "doctor_id",
            referencedColumnName = "id")
    private Doctor doctor;

    @Column(name = "monday")
    private Boolean monday;

    @Column(name = "tuesday")
    private Boolean tuesday;

    @Column(name = "wednesday")
    private Boolean wednesday;

    @Column(name = "thursday")
    private Boolean thursday;

    @Column(name = "friday")
    private Boolean friday;

    @Column(name = "saturday")
    private Boolean saturday;

    @Column(name = "sunday")
    private Boolean sunday;

//    public void setDoctor(Doctor doctor){
//        doctor.setSchedule(this);
//        this.doctor = doctor;
//    }
}
