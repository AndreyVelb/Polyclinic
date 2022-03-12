package repository;

import entity.DoctorsAppointment;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class DoctorsAppointmentRepository extends AbstractRepository<Long, DoctorsAppointment>{

    public DoctorsAppointmentRepository() {
        super(DoctorsAppointment.class);
    }

    public Optional<LocalDateTime> getLatestAppointmentDate(Session session){
        return Optional.ofNullable(session.createQuery("select max(docApp.appointmentDateTime) " +
                                             "from DoctorsAppointment docApp", LocalDateTime.class).uniqueResult());
    }
}
