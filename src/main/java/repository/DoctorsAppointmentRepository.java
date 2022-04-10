package repository;

import entity.DoctorsAppointment;
import entity.Patient;
import exception.NotFoundException;
import lombok.SneakyThrows;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DoctorsAppointmentRepository extends AbstractRepository<Long, DoctorsAppointment>{

    public DoctorsAppointmentRepository() {
        super(DoctorsAppointment.class);
    }

    @SneakyThrows
    public void bookDoctorsAppointment(Long docAppId, Patient patient, Session session){
        Optional<DoctorsAppointment> mayBeDoctorAppointment = findById(docAppId, session);
        if (mayBeDoctorAppointment.isPresent()){
            DoctorsAppointment doctorsAppointment = mayBeDoctorAppointment.get();
            doctorsAppointment.setPatient(patient);
            session.update(doctorsAppointment);
        } else throw new NotFoundException();
    }

    public List<DoctorsAppointment> getVacantAppointmentsAtDoctor(Long doctorId, Session session){
        return session.createQuery("select docApp from DoctorsAppointment docApp " +
                "where doctor.id = :doctorId and patient = null", DoctorsAppointment.class)
                .setParameter("doctorId", doctorId)
                .getResultList();
    }

    public Optional<LocalDateTime> getLatestAppointmentDate(Session session){
        return Optional.ofNullable(session.createQuery("select max (docApp.appointmentDateTime) " +
                                             "from DoctorsAppointment docApp", LocalDateTime.class).uniqueResult());
    }
}
