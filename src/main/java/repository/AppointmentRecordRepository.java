package repository;

import entity.AppointmentRecord;
import org.hibernate.Session;

public class AppointmentRecordRepository extends AbstractRepository<Long, AppointmentRecord> {
    public AppointmentRecordRepository(Session session) {
        super(AppointmentRecord.class, session);
    }
}
