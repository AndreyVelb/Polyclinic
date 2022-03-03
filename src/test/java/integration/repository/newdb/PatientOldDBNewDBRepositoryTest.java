package integration.repository.newdb;

import entity.newdb.PatientNewDB;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.newdb.PatientNewDBRepository;
import integration.util.HibernateTestUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

class PatientOldDBNewDBRepositoryTest implements NEWdbRepositoryTest<Long, PatientNewDB> {
    private static SessionFactory sessionFactory;
    private static Session session;
    private static PatientNewDBRepository repository;

    @BeforeAll
    static void open(){
        sessionFactory = HibernateTestUtil.buildTestSessionFactoryForNEWdb();
        session = sessionFactory.openSession();
        repository = new PatientNewDBRepository(session);
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
            PatientNewDB entity = createEntity();
            repository.save(entity);
            session.flush();
            session.clear();
            entity.setFirstName("Иван");
            repository.update(entity);
            session.flush();
            session.clear();
            PatientNewDB mayBeUpdatingEntity = returnsFirstInstanceFromDB();
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
            PatientNewDB entity = createEntity();
            repository.save(entity);
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
            PatientNewDB entity = createEntity();
            repository.save(entity);
            if(isDBNotEmpty()){
                repository.delete(returnsFirstInstanceFromDB().getId());
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
            PatientNewDB entity = createEntity();
            repository.save(entity);
            session.clear();
            Long idForTestingMethod = returnsFirstInstanceFromDB().getId();
            Optional<PatientNewDB> mayBeEntity =  repository.findById(idForTestingMethod);
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
            PatientNewDB entity1 = createEntity();
            repository.save(entity1);
            PatientNewDB entity2 = createEntity();
            repository.save(entity2);
            ArrayList<PatientNewDB> entities = findAllEntities();
            Assertions.assertEquals(2, entities.size());
        }
        else Assertions.fail();
        session.getTransaction().rollback();
    }

    @Override
    public PatientNewDB returnsFirstInstanceFromDB() {
        return findAllEntities().get(0);
    }

    @Override
    public boolean isDBEmpty() {
        return findAllEntities().size() == 0;
    }

    @Override
    public boolean isDBNotEmpty() {
        ArrayList<PatientNewDB> entities = findAllEntities();
        return entities.size() != 0;
    }

    @Override
    public ArrayList<PatientNewDB> findAllEntities() {
        return repository.findAll();
    }

    @Override
    public PatientNewDB createEntity() {
        PatientNewDB patientNewDB = PatientNewDB.builder()
                .lastName("Сидоров")
                .firstName("Игорь")
                .middleName("Игоревич")
                .birthDate(LocalDate.of(1997, 11, 14))
                .build();
        return patientNewDB;
    }
}