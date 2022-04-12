package repository;

import entity.DoctorsAppointment;
import entity.Patient;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DoctorsAppointmentRepository extends AbstractRepository<Long, DoctorsAppointment> {

    public DoctorsAppointmentRepository() {
        super(DoctorsAppointment.class);
    }

    public void bookDoctorsAppointment(DoctorsAppointment doctorsAppointment, Patient patient, Session session) {
        doctorsAppointment.setPatient(patient);
        session.update(doctorsAppointment);
    }

    public List<DoctorsAppointment> getVacantAppointmentsAtDoctor(Long doctorId, Session session) {
        return session.createQuery("select docApp from DoctorsAppointment docApp " +
                        "where doctor.id = :doctorId and patient = null", DoctorsAppointment.class)
                .setParameter("doctorId", doctorId)
                .getResultList();
    }

    public Optional<LocalDateTime> getLatestAppointmentDate(Session session) {
        return Optional.ofNullable(session.createQuery("select max (docApp.appointmentDateTime) " +
                "from DoctorsAppointment docApp", LocalDateTime.class).uniqueResult());
    }
}
