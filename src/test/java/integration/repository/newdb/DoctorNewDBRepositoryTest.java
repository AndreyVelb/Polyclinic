package integration.repository.newdb;

import entity.DoctorSpeciality;
import entity.newdb.DoctorNewDB;
import entity.newdb.Qualification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import repository.newdb.DoctorNewDBRepository;
import integration.util.HibernateTestUtil;

import java.util.ArrayList;
import java.util.Optional;

//class DoctorNewDBRepositoryTest implements NEWdbRepositoryTest<Long, DoctorNewDB> {
//    private static SessionFactory sessionFactory;
//    private static Session session;
//    private static DoctorNewDBRepository repository;
//
//    @BeforeAll
//    static void open(){
//        sessionFactory = HibernateTestUtil.buildTestSessionFactoryForNEWdb();
//        session = sessionFactory.openSession();
//        repository = new DoctorNewDBRepository(session);
//    }
//
//    @AfterAll
//     static void close(){
//        session.close();
//        sessionFactory.close();
//    }
//
//    @Test
//    void findByLogin(){
//        session.beginTransaction();
//        DoctorNewDB doctorNewDB = createEntity();
//        Assertions.assertTrue(repository.findByLogin(doctorNewDB.getLogin()).isEmpty());
//        repository.save(doctorNewDB);
//        if(isDBNotEmpty()){
//            Assertions.assertTrue(repository.findByLogin(doctorNewDB.getLogin()).isPresent());
//        }
//        else Assertions.fail();
//    }
//
//    @Test
//    void authentication() {
//        session.beginTransaction();
//        if (isDBEmpty()){
//            DoctorNewDB doctorNewDB = createEntity();
//            repository.save(doctorNewDB);
//            if(isDBNotEmpty()){
//                Assertions.assertTrue((repository.authentication(doctorNewDB.getLogin(), doctorNewDB.getPassword())).isPresent());
//                Assertions.assertTrue((repository.authentication("login", "password")).isPresent());
//                Assertions.assertTrue((repository.authentication(doctorNewDB.getLogin(), "passwor")).isEmpty());
//                Assertions.assertTrue((repository.authentication("log", "pass11222222222223")).isEmpty());
//            }
//            else Assertions.fail();
//            session.getTransaction().rollback();
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//    }
//
//    @Test
//    @Override
//    public void update() {
//        session.beginTransaction();
//        if (isDBEmpty()){
//            DoctorNewDB entity = createEntity();
//            repository.save(entity);
//            session.flush();
//            session.clear();
//            entity.setFirstName("Егор");
//            repository.update(entity);
//            session.flush();
//            session.clear();
//            DoctorNewDB mayBeUpdatingEntity = returnsFirstInstanceFromDB();
//            if(isDBNotEmpty()){
//                Assertions.assertEquals("Егор", mayBeUpdatingEntity.getFirstName());
//            }
//            else Assertions.fail();
//            session.getTransaction().rollback();
//        }
//
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
//            DoctorNewDB entity = createEntity();
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
//            DoctorNewDB entity = createEntity();
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
//            DoctorNewDB entity = createEntity();
//            repository.save(entity);
//            session.clear();
//            Long idForTestingMethod = returnsFirstInstanceFromDB().getId();
//            Optional<DoctorNewDB> mayBeEntity =  repository.findById(idForTestingMethod);
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
//            DoctorNewDB entity1 = createEntity();
//            repository.save(entity1);
//            DoctorNewDB entity2 = createEntity();
//            repository.save(entity2);
//            ArrayList<DoctorNewDB> entities = findAllEntities();
//            Assertions.assertEquals(2, entities.size());
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//    }
//
//    @Override
//    public DoctorNewDB returnsFirstInstanceFromDB() {
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
//    public ArrayList<DoctorNewDB> findAllEntities() {
//        return repository.findAll();
//    }
//
//    public DoctorNewDB createEntity() {
//        DoctorNewDB doctorNewDB = DoctorNewDB.builder()
//                .lastName("Иванов")
//                .firstName("Иван")
//                .middleName("Иванович")
//                .qualification(Qualification.FIRST)
//                .speciality(DoctorSpeciality.NEUROLOGIST)
//                .login("login")
//                .password("password")
//                .build();
//        return doctorNewDB;
//    }
//}