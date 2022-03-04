package repository;

import entity.Patient;
import org.hibernate.Session;
import exception.ServerTechnicalProblemsException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class PatientRepository extends AbstractRepository<Long, Patient> {
    private final Session session;

    public PatientRepository(Session session) {
        super(Patient.class, session);
        this.session = session;
    }

    public boolean registerPatient(Patient patient) throws ServerTechnicalProblemsException{
        try {
            session.createNativeQuery("LOCK TABLE patients IN ROW EXCLUSIVE MODE").executeUpdate();
            if((findByLogin(patient.getLogin())).isEmpty()){
                session.save(patient);
                session.flush();
                return true;
            } else return false;
        } catch (Exception e){
            throw new ServerTechnicalProblemsException();
        }
    }

    public Optional<Patient> authenticate(String login, String password) {
        return Optional.ofNullable(session.createQuery("select patient from Patient patient " +
                        "where patient.login = :login and patient.password = (crypt(:password, patient.password))", Patient.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult());
    }

    public Optional<Patient> findByFullNameAndBirthday(String desiredLastName, String desiredFirstName, String desiredMiddleName, LocalDate desiredBirthday){
        return Optional.ofNullable(session.createQuery("select patient from Patient patient " +
                "where patient.lastName =: lastName and patient.firstName =: firstName and patient.middleName =: middleName " +
                "and patient.birthDate =: birthDate", Patient.class)
                .setParameter("lastName", desiredLastName)
                .setParameter("firstName", desiredFirstName)
                .setParameter("middleName", desiredMiddleName)
                .setParameter("birthDate", desiredBirthday)
                .uniqueResult());
    }

    public ArrayList<Patient> findByLastName(String lastName){
        return (ArrayList<Patient>) session.createQuery("select patient from Patient patient " +
                "where patient.lastName =: lastName", Patient.class)
                .setParameter("lastName", lastName)
                .list();
    }

    public Optional<Patient> findByLogin(String login){
        return (Optional<Patient>) Optional.ofNullable(session.createQuery("select  patient from Patient patient " +
                "where patient.login =: login", Patient.class)
                .setParameter("login", login)
                .uniqueResult());
    }
}