package repository.newdb;

import entity.newdb.DoctorNewDB;
import org.hibernate.Session;

import java.util.Optional;

public class DoctorNewDBRepository extends RepositoryBaseNewDB<Long, DoctorNewDB> {
    private final Session session;

    public DoctorNewDBRepository(Session session) {
        super(DoctorNewDB.class, session);
        this.session = session;
    }

    public Optional<DoctorNewDB> authenticate(String login, String password) {
        return Optional.ofNullable(session.createQuery("select doctor from DoctorNewDB doctor " +
                        "where doctor.login = :login and doctor.password = (crypt(:password, doctor.password))", DoctorNewDB.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult());
    }

    public Optional<DoctorNewDB> findByLogin(String login){
        Optional<DoctorNewDB> mayBeDoctor = Optional.ofNullable(session.createQuery("select doctor from DoctorNewDB doctor " +
                        "where doctor.login = :login", DoctorNewDB.class)
                .setParameter("login", login)
                .uniqueResult());
        return mayBeDoctor;
    }


}
