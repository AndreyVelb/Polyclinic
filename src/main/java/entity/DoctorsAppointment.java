package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "timetable")
@OptimisticLocking(type = OptimisticLockType.ALL)
@DynamicUpdate
public class DoctorsAppointment implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id")
    @Fetch(FetchMode.JOIN)
    private Doctor doctor;

    @Column(name = "date_and_time")
    private LocalDateTime appointmentDateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patient;
}
