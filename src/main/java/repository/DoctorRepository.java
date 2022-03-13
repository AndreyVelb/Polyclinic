package repository;

import entity.Doctor;
import entity.Patient;
import exception.ServerTechnicalProblemsException;
import org.hibernate.Session;

import javax.print.Doc;
import java.util.Optional;


public class DoctorRepository extends AbstractRepository<Long, Doctor>{

    public DoctorRepository() {
        super(Doctor.class);
    }

    public boolean registerDoctor(Doctor doctor, Session session) throws ServerTechnicalProblemsException {
        try {
            session.createNativeQuery("LOCK TABLE doctors IN ROW EXCLUSIVE MODE").executeUpdate();
            if((findByLogin(doctor.getLogin(), session).isEmpty())){
                session.save(doctor);
                session.flush();
                return true;
            } else return false;
        } catch (Exception e){
            throw new ServerTechnicalProblemsException();
        }
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
