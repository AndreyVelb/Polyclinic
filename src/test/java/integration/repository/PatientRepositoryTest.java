package integration.repository;

import entity.Patient;
import integration.repository.RepositoryTest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.PatientRepository;
import integration.util.HibernateTestUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

class PatientRepositoryTest implements RepositoryTest<Long, Patient> {
    private static SessionFactory sessionFactory;
    private static Session session;
    private static PatientRepository repository;

    @BeforeAll
    static void open(){
        sessionFactory = HibernateTestUtil.buildTestSessionFactory();
        session = sessionFactory.openSession();
        repository = new PatientRepository();
    }

    @AfterAll
    static void close(){
        session.close();
        sessionFactory.close();
    }


    @Test
    @Override
    public void update() {
        session.beginTransaction();
        if (isDBEmpty()){
            Patient entity = createEntity();
            repository.save(entity, session);
            session.flush();
            session.clear();
            entity.setFirstName("Иван");
            repository.update(entity, session);
            session.flush();
            session.clear();
            Patient mayBeUpdatingEntity = returnsFirstInstanceFromDB();
            if(isDBNotEmpty()){
                Assertions.assertEquals("Иван", mayBeUpdatingEntity.getFirstName());
            }
            else Assertions.fail();
        }
        else Assertions.fail();
        session.getTransaction().rollback();
    }

    @Test
    @Override
    public void save() {
        session.beginTransaction();
        if(isDBEmpty()){
            Patient entity = createEntity();
            repository.save(entity, session);
            Assertions.assertTrue(isDBNotEmpty());
        }
        else Assertions.fail();
        session.getTransaction().rollback();
    }

    @Test
    @Override
    public void delete() {
        session.beginTransaction();
        if(isDBEmpty()){
            Patient entity = createEntity();
            repository.save(entity, session);
            if(isDBNotEmpty()){
                repository.delete(returnsFirstInstanceFromDB().getId(), session);
                Assertions.assertTrue(isDBEmpty());
            }
            else Assertions.fail();
        }
        else Assertions.fail();
        session.getTransaction().rollback();
    }

    @Test
    @Override
    public void findById() {
        session.beginTransaction();
        if (isDBEmpty()){
            Patient entity = createEntity();
            repository.save(entity, session);
            session.clear();
            Long idForTestingMethod = returnsFirstInstanceFromDB().getId();
            Optional<Patient> mayBeEntity =  repository.findById(idForTestingMethod, session);
            Assertions.assertTrue(mayBeEntity.isPresent());
        }
        else Assertions.fail();
        session.getTransaction().rollback();
    }

    @Test
    @Override
    public void findAll() {
        session.beginTransaction();
        if(isDBEmpty()){
            Patient entity1 = createEntity();
            repository.save(entity1, session);
            Patient entity2 = Patient.builder()
                    .lastName("Сидоров")
                    .firstName("Игорь")
                    .middleName("Игоревич")
                    .login("anotherLogin")
                    .password("password")
                    .birthDate(LocalDate.of(1997, 11, 14))
                    .build();;
            repository.save(entity2, session);
            ArrayList<Patient> entities = findAllEntities();
            Assertions.assertEquals(2, entities.size());
        }
        else Assertions.fail();
        session.getTransaction().rollback();
    }

    @Override
    public Patient returnsFirstInstanceFromDB() {
        return findAllEntities().get(0);
    }

    @Override
    public boolean isDBEmpty() {
        return findAllEntities().size() == 0;
    }

    @Override
    public boolean isDBNotEmpty() {
        ArrayList<Patient> entities = findAllEntities();
        return entities.size() != 0;
    }

    @Override
    public ArrayList<Patient> findAllEntities() {
        return repository.findAll(session);
    }

    @Override
    public Patient createEntity() {
        return Patient.builder()
                .lastName("Сидоров")
                .firstName("Игорь")
                .middleName("Игоревич")
                .login("login")
                .password("password")
                .birthDate(LocalDate.of(1997, 11, 14))
                .build();
    }
}