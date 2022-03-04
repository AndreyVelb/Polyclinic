package repository;

import entity.Doctor;
import org.hibernate.Session;

import java.util.Optional;

public class DoctorRepository extends AbstractRepository<Long, Doctor> {
    private final Session session;

    public DoctorRepository(Session session) {
        super(Doctor.class, session);
        this.session = session;
    }

    public Optional<Doctor> authenticate(String login, String password) {
        return Optional.ofNullable(session.createQuery("select doctor from Doctor doctor " +
                        "where doctor.login = :login and doctor.password = (crypt(:password, doctor.password))", Doctor.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult());
    }

    public Optional<Doctor> findByLogin(String login){
        return Optional.ofNullable(session.createQuery("select doctor from Doctor doctor " +
                        "where doctor.login = :login", Doctor.class)
                .setParameter("login", login)
                .uniqueResult());
    }


}
