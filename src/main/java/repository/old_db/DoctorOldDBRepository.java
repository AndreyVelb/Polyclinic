package repository.old_db;

import entity.olddb.DoctorOldDB;
import org.hibernate.Session;

public class DoctorOldDBRepository extends RepositoryBaseOldDB {
    public DoctorOldDBRepository(Session session) {
        super(DoctorOldDB.class, session);
    }
}
