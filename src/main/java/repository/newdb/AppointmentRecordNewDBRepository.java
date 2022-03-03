package repository.newdb;

import entity.newdb.AppointmentRecordNewDB;
import org.hibernate.Session;

public class AppointmentRecordNewDBRepository extends RepositoryBaseNewDB<Long, AppointmentRecordNewDB>{
    public AppointmentRecordNewDBRepository(Session session) {
        super(AppointmentRecordNewDB.class, session);
    }
}
