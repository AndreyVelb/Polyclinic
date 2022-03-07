package service.admin;

import entity.Patient;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import repository.DoctorRepository;
import repository.PatientRepository;
import repository.Repository;
import util.SessionPool;

@RequiredArgsConstructor
public class HomePageService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public int getNumbersOfSubjects(Repository repository){
        Session session = SessionPool.getSession();
        try {
            session.beginTransaction();
            return repository.findAll(session).size();
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }
}
