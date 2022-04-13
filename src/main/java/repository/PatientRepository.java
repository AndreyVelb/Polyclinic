package repository;

import entity.Patient;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Optional;

public class PatientRepository extends AbstractRepository<Long, Patient> {


    public PatientRepository() {
        super(Patient.class);

    }

    public Optional<Patient> authenticate(String login, String password, Session session) {
        return Optional.ofNullable(session.createQuery("select patient from Patient patient " +
                        "where patient.login = :login and patient.password = (crypt(:password, patient.password))", Patient.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult());
    }

    public ArrayList<Patient> findByLastName(String lastName, Session session) {
        return (ArrayList<Patient>) session.createQuery("select patient from Patient patient " +
                        "where patient.lastName =: lastName", Patient.class)
                .setParameter("lastName", lastName)
                .list();
    }

    public Optional<Patient> findByLogin(String login, Session session) {
        return Optional.ofNullable(session.createQuery("select  patient from Patient patient " +
                        "where patient.login =: login", Patient.class)
                .setParameter("login", login)
                .uniqueResult());
    }
}
