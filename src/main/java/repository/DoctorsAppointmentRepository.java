package repository;

import entity.DoctorsAppointment;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DoctorsAppointmentRepository extends AbstractRepository<Long, DoctorsAppointment>{

    public DoctorsAppointmentRepository(Class<DoctorsAppointment> clazz) {
        super(clazz);
    }

    public LocalDate getLatestAppointmentDate(Session session){
        LocalDateTime maxLocalDateTime = session.createQuery("select max(docApp.appointmentDateTime) " +
                                             "from DoctorsAppointment docApp", LocalDateTime.class).uniqueResult();
        return maxLocalDateTime.toLocalDate();
    }
}
