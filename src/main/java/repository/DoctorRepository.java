package repository;

import entity.Doctor;
import org.hibernate.Session;

import javax.print.Doc;
import java.util.Optional;


public class DoctorRepository extends AbstractRepository<Long, Doctor>{

    public DoctorRepository() {
        super(Doctor.class);
    }

    public Optional<Doctor> authenticate(String login, String password, Session session) {
        return Optional.ofNullable(session.createQuery("select doctor from Doctor doctor " +
                        "where doctor.login = :login and doctor.password = (crypt(:password, doctor.password))", Doctor.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult());
    }

    public Optional<Doctor> findByLogin(String login, Session session){
        return Optional.ofNullable(session.createQuery("select doctor from Doctor doctor " +
                        "where doctor.login = :login", Doctor.class)
                .setParameter("login", login)
                .uniqueResult());
    }
}
