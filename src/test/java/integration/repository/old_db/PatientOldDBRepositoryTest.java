package integration.repository.old_db;

import entity.olddb.PatientOldDB;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.old_db.PatientOldDBRepository;
import integration.util.HibernateTestUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

//class PatientOldDBRepositoryTest implements OLDdbRepositoryTest<Long, PatientOldDB> {
//    private static SessionFactory sessionFactory;
//    private static Session session;
//    private static PatientOldDBRepository repository;
//
//    @BeforeAll
//    static void open(){
//        sessionFactory = HibernateTestUtil.buildTestSessionFactoryForOLDdb();
//        session = sessionFactory.openSession();
//        repository = new PatientOldDBRepository(session);
//    }
//
//    @AfterAll
//    static void close(){
//        session.close();
//        sessionFactory.close();
//    }
//
//    @Test
//    @Override
//    public void update() {
//        session.beginTransaction();
//        if(isDBEmpty()){
//            PatientOldDB entity = createEntity();
//            repository.save(entity);
//            session.flush();
//            session.clear();
//            entity.setFirstName("Иван");
//            repository.update(entity);
//            session.flush();
//            session.clear();
//            PatientOldDB mayBeUpdatingEntity = returnsFirstInstanceFromDB();
//            if(isDBNotEmpty()){
//                Assertions.assertEquals("Иван", mayBeUpdatingEntity.getFirstName());
//            }
//            else Assertions.fail();
//            session.getTransaction().rollback();
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//    }
//
//
//    @Test
//    @Override
//    public void save() {
//        session.beginTransaction();
//        if(isDBEmpty()){
//            PatientOldDB entity = createEntity();
//            repository.save(entity);
//            Assertions.assertTrue(isDBNotEmpty());
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//    }
//
//    @Test
//    @Override
//    public void delete() {
//        session.beginTransaction();
//        if(isDBEmpty()){
//            PatientOldDB entity = createEntity();
//            repository.save(entity);
//            if(isDBNotEmpty()){
//                repository.delete(returnsFirstInstanceFromDB().getId());
//                Assertions.assertTrue(isDBEmpty());
//            }
//            else Assertions.fail();
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//    }
//
//    @Test
//    @Override
//    public void findById() {
//        session.beginTransaction();
//        if (isDBEmpty()){
//            PatientOldDB entity = createEntity();
//            repository.save(entity);
//            session.clear();
//            Long idForTestingMethod = returnsFirstInstanceFromDB().getId();
//            Optional<PatientOldDB> mayBeEntity =  repository.findById(idForTestingMethod);
//            Assertions.assertTrue(mayBeEntity.isPresent());
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//    }
//
//    @Test
//    @Override
//    public void findAll() {
//        session.beginTransaction();
//        if(isDBEmpty()){
//            PatientOldDB entity1 = createEntity();
//            repository.save(entity1);
//            PatientOldDB entity2 = createEntity();
//            repository.save(entity2);
//            ArrayList<PatientOldDB> entities = findAllEntities();
//            Assertions.assertEquals(2, entities.size());
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//    }
//
//    @Override
//    public PatientOldDB createEntity() {
//        PatientOldDB patient = PatientOldDB.builder()
//                .lastName("Шумилев")
//                .firstName("Алексей")
//                .middleName("Петрович")
//                .birthDate(LocalDate.of(1993, 3, 14))
//                .build();
//        repository.save(patient);
//        return patient;
//    }
//
//    @Override
//    public PatientOldDB returnsFirstInstanceFromDB() {
//        return findAllEntities().get(0);
//    }
//
//    @Override
//    public boolean isDBEmpty() {
//        return findAllEntities().size() == 0;
//    }
//
//    @Override
//    public boolean isDBNotEmpty() {
//        return findAllEntities().size() != 0;
//    }
//
//    @Override
//    public ArrayList<PatientOldDB> findAllEntities() {
//        return repository.findAll();
//    }
//}