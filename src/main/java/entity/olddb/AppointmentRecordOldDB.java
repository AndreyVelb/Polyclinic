package entity.olddb;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reception_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRecordOldDB implements BaseEntityOldDB<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_id")
    private PatientOldDB patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id")
    private DoctorOldDB doctor;

    @Column(name = "reception_date")
    private LocalDate receptionDate;

    @Column(name = "health_complaints")
    private String healthComplaints;

    @Column(name = "doctors_recommendation")
    private String doctorsRecommendation;

    @Override
    public String toString() {
        return "---------------------------------------------------------------------------------------------\n"
                + "Ф.И.О. ПАЦИЕНТА: " + "\n\t" + patient.getLastName() + " " + patient.getFirstName()
                + " " + patient.getMiddleName() + "\n" +
                "Ф.И.О. ВРАЧА: " + "\n\t" + doctor.getLastName() + " " + doctor.getFirstName()
                + " " + doctor.getMiddleName() + "\n" +
                "ДАТА ПОСЕЩЕНИЯ: " + "\n\t" + receptionDate + "\n" +
                "ПРИЧИНА ПОСЕЩЕНИЯ / ЖАЛОБЫ ПАЦИЕНТА: " + "\n\t" + getHealthComplaints() + "\n" +
                "РЕКОМЕНДАЦИИ ВРАЧА: " + "\n\t" + getDoctorsRecommendation() + "\n"
                + "---------------------------------------------------------------------------------------------";

    }
}
