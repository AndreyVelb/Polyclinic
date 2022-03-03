package entity.newdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "appointment_records")
public class AppointmentRecordNewDB implements BaseEntityNewDB<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id")
    private PatientNewDB patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id")
    private DoctorNewDB doctor;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(name = "health_complaints")
    private String healthComplaints;

    @Column(name = "doctors_recommendation")
    private String doctorsRecommendation;

}
