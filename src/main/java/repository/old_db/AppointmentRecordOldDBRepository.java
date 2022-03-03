package repository.old_db;

import entity.olddb.AppointmentRecordOldDB;
import org.hibernate.Session;

public class AppointmentRecordOldDBRepository extends RepositoryBaseOldDB {
    public AppointmentRecordOldDBRepository(Session session) {
        super(AppointmentRecordOldDB.class, session);
    }
}
