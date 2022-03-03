package repository.newdb;

import entity.newdb.PatientNewDB;
import org.hibernate.Session;
import exception.ServerTechnicalProblemsException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class PatientNewDBRepository extends RepositoryBaseNewDB<Long, PatientNewDB>{
    private final Session session;

    public PatientNewDBRepository(Session session) {
        super(PatientNewDB.class, session);
        this.session = session;
    }

    public boolean registerPatient(PatientNewDB patient) throws ServerTechnicalProblemsException{
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

    public Optional<PatientNewDB> authenticate(String login, String password) {
        return Optional.ofNullable(session.createQuery("select patient from PatientNewDB patient " +
                        "where patient.login = :login and patient.password = (crypt(:password, patient.password))", PatientNewDB.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult());
    }

    public Optional<PatientNewDB> findByFullNameAndBirthday(String desiredLastName, String desiredFirstName, String desiredMiddleName, LocalDate desiredBirthday){
        return Optional.ofNullable(session.createQuery("select patient from PatientNewDB patient " +
                "where patient.lastName =: lastName and patient.firstName =: firstName and patient.middleName =: middleName " +
                "and patient.birthDate =: birthDate", PatientNewDB.class)
                .setParameter("lastName", desiredLastName)
                .setParameter("firstName", desiredFirstName)
                .setParameter("middleName", desiredMiddleName)
                .setParameter("birthDate", desiredBirthday)
                .uniqueResult());
    }

    public ArrayList<PatientNewDB> findByLastName(String lastName){
        return (ArrayList<PatientNewDB>) session.createQuery("select patient from PatientNewDB patient " +
                "where patient.lastName =: lastName", PatientNewDB.class)
                .setParameter("lastName", lastName)
                .list();
    }

    public Optional<PatientNewDB> findByLogin(String login){
        return (Optional<PatientNewDB>) Optional.ofNullable(session.createQuery("select  patient from PatientNewDB patient " +
                "where patient.login =: login", PatientNewDB.class)
                .setParameter("login", login)
                .uniqueResult());
    }
}
