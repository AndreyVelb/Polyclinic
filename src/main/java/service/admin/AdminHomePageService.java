package service.admin;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import repository.DoctorRepository;
import repository.PatientRepository;
import repository.Repository;
import util.SessionPool;

@RequiredArgsConstructor
public class AdminHomePageService {

    public int getNumbersOfSubjects(Repository repository){
        Session session = SessionPool.getSession();
        try {
            session.beginTransaction();
            int size = repository.findAll(session).size();
            session.getTransaction().commit();
            return size;
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }
}
