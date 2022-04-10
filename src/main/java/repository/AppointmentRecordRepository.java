package repository;

import entity.AppointmentRecord;
import org.hibernate.Session;

import java.util.Optional;

public class AppointmentRecordRepository extends AbstractRepository<Long, AppointmentRecord> {


    public AppointmentRecordRepository() {
        super(AppointmentRecord.class);

    }

    public Optional<AppointmentRecord> findByDoctorPatientAndDate(AppointmentRecord appointmentRecord, Session session){
        return Optional.ofNullable(session.createQuery("select record from AppointmentRecord record " +
                        "where record.doctor.id = :doctorId and record.patient.id = :patientId " +
                        "and record.visitDate = :visitDate", AppointmentRecord.class)
                .setParameter("doctorId", appointmentRecord.getDoctor().getId())
                .setParameter("patientId", appointmentRecord.getPatient().getId())
                .setParameter("visitDate", appointmentRecord.getVisitDate())
                .uniqueResult());
    }
}
