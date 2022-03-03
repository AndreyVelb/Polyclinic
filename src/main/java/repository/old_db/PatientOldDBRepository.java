package repository.old_db;

import entity.olddb.PatientOldDB;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Optional;

public class PatientOldDBRepository extends RepositoryBaseOldDB {
    Session session;
    
    public PatientOldDBRepository(Session session) {
        super(PatientOldDB.class, session);
    }

    public Optional<PatientOldDB> findByFullNameAndBirthdate(String desiredLastName, String desiredFirstName, String desiredMiddleName, LocalDate desiredBirthday){
        return Optional.ofNullable(session.createQuery("select patient from PatientOldDB patient " +
                        "where patient.lastName =: lastName and patient.firstName =: firstName and patient.middleName =: middleName " +
                        "and patient.birthDate =: birthDate", PatientOldDB.class)
                .setParameter("lastName", desiredLastName)
                .setParameter("firstName", desiredFirstName)
                .setParameter("middleName", desiredMiddleName)
                .setParameter("birthDate", desiredBirthday)
                .uniqueResult());
    }
}
