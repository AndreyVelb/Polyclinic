package repository;

import entity.AppointmentRecord;
import entity.Doctor;
import org.hibernate.Session;

import java.util.Optional;

public class AppointmentRecordRepository extends AbstractRepository<Long, AppointmentRecord> {
    private final Session session;

    public AppointmentRecordRepository(Session session) {
        super(AppointmentRecord.class, session);
        this.session = session;
    }

    public Optional<AppointmentRecord> findByDoctorPatientAndDate(AppointmentRecord appointmentRecord){
        return Optional.ofNullable(session.createQuery("select record from AppointmentRecord record " +
                        "where record.doctor.id = :doctorId and record.patient.id = :patientId " +
                        "and record.visitDate = :visitDate", AppointmentRecord.class)
                .setParameter("doctorId", appointmentRecord.getDoctor().getId())
                .setParameter("patientId", appointmentRecord.getPatient().getId())
                .setParameter("visitDate", appointmentRecord.getVisitDate())
                .uniqueResult());
    }
}
