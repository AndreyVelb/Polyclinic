package integration.repository.old_db;

import entity.DoctorSpeciality;
import entity.olddb.DoctorOldDB;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.old_db.DoctorOldDBRepository;
import integration.util.HibernateTestUtil;

import java.util.ArrayList;
import java.util.Optional;

//class DoctorOldDBRepositoryTest implements OLDdbRepositoryTest<Long, DoctorOldDB> {
//    private static SessionFactory sessionFactory;
//    private static Session session;
//    private static DoctorOldDBRepository repository;
//
//    @BeforeAll
//    static void open(){
//        sessionFactory = HibernateTestUtil.buildTestSessionFactoryForOLDdb();
//        session = sessionFactory.openSession();
//        repository = new DoctorOldDBRepository(session);
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
//            DoctorOldDB doctor = createEntity();
//            repository.save(doctor);
//            session.flush();
//            doctor.setFirstName("Иван");
//            repository.update(doctor);
//            session.flush();
//            session.clear();
//            DoctorOldDB mayBeUpdatingDoctor = returnsFirstInstanceFromDB();
//            if(isDBNotEmpty()){
//                Assertions.assertEquals("Иван", mayBeUpdatingDoctor.getFirstName());
//            }
//            else Assertions.fail();
//            session.getTransaction().rollback();
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//
//    }
//
//    @Test
//    @Override
//    public void save() {
//        session.beginTransaction();
//        if(isDBEmpty()){
//            DoctorOldDB entity = createEntity();
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
//            DoctorOldDB entity = createEntity();
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
//            DoctorOldDB entity = createEntity();
//            repository.save(entity);
//            session.clear();
//            Long idForTestingMethod = returnsFirstInstanceFromDB().getId();
//            Optional<DoctorOldDB> mayBeEntity =  repository.findById(idForTestingMethod);
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
//            DoctorOldDB entity1 = createEntity();
//            repository.save(entity1);
//            DoctorOldDB entity2 = createEntity();
//            repository.save(entity2);
//            ArrayList<DoctorOldDB> entities = findAllEntities();
//            Assertions.assertEquals(2, entities.size());
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//    }
//
//    @Override
//    public DoctorOldDB createEntity() {
//        DoctorOldDB doctor = DoctorOldDB.builder()
//                .lastName("Алферов")
//                .firstName("Жорес")
//                .middleName("Иванович")
//                .speciality(DoctorSpeciality.GENERAL_DOCTOR)
//                .build();
//        return doctor;
//    }
//
//    @Override
//    public DoctorOldDB returnsFirstInstanceFromDB() {
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
//    public ArrayList<DoctorOldDB> findAllEntities() {
//        return repository.findAll();
//    }
//
//}