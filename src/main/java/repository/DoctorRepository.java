package repository;

import entity.Doctor;
import entity.DoctorSpeciality;
import exception.NotFoundException;
import exception.ServerTechnicalProblemsException;
import exception.UserAlreadyExistsException;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;


public class DoctorRepository extends AbstractRepository<Long, Doctor>{

    public DoctorRepository() {
        super(Doctor.class);
    }

    public Long registerDoctor(Doctor doctor, Session session) throws ServerTechnicalProblemsException {
        try {
            return (Long) session.save(doctor);
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
