package repository;

import entity.Doctor;
import entity.DoctorSpeciality;
import entity.Patient;
import exception.ServerTechnicalProblemsException;
import org.hibernate.Session;

import javax.print.Doc;
import java.util.List;
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

    public List<Doctor> findAllDoctorsExceptAdmins(Session session){
        return session.createQuery("select doctor from Doctor doctor " +
        "where doctor.speciality != : adminSpecialty", Doctor.class)
                .setParameter("adminSpecialty", DoctorSpeciality.CHIEF_DOCTOR)
                .getResultList();
    }

    public Optional<Doctor> findByLogin(String login, Session session){
        return Optional.ofNullable(session.createQuery("select doctor from Doctor doctor " +
                        "where doctor.login = :login", Doctor.class)
                .setParameter("login", login)
                .uniqueResult());
    }
}
