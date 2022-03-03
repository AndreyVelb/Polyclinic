package integration.repository.old_db;

import entity.DoctorSpeciality;
import entity.olddb.DoctorOldDB;
import entity.olddb.PatientOldDB;
import entity.olddb.AppointmentRecordOldDB;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.old_db.DoctorOldDBRepository;
import repository.old_db.PatientOldDBRepository;
import repository.old_db.AppointmentRecordOldDBRepository;
import integration.util.HibernateTestUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

//class AppointmentRecordOldDBRepositoryTest implements OLDdbRepositoryTest<Long, AppointmentRecordOldDB>{
//    private static SessionFactory sessionFactory;
//    private static Session session;
//    private static DoctorOldDBRepository doctorRepository;
//    private static PatientOldDBRepository patientRepository;
//    private static AppointmentRecordOldDBRepository receptionResultRepository;
//
//    @BeforeAll
//    static void open(){
//        sessionFactory = HibernateTestUtil.buildTestSessionFactoryForOLDdb();
//        session = sessionFactory.openSession();
//        doctorRepository = new DoctorOldDBRepository(session);
//        patientRepository = new PatientOldDBRepository(session);
//        receptionResultRepository = new AppointmentRecordOldDBRepository(session);
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
//            AppointmentRecordOldDB entity = createEntity();
//            receptionResultRepository.save(entity);
//            session.flush();
//            entity.setHealthComplaints("Насморк");
//            receptionResultRepository.update(entity);
//            session.flush();
//            session.clear();
//            AppointmentRecordOldDB mayBeUpdatingEntity = returnsFirstInstanceFromDB();
//            if(isDBNotEmpty()){
//                Assert.assertEquals("Насморк", mayBeUpdatingEntity.getHealthComplaints());
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
//    public void save() {
//        session.beginTransaction();
//        if(isDBEmpty()){
//            AppointmentRecordOldDB entity = createEntity();
//            receptionResultRepository.save(entity);
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
//            AppointmentRecordOldDB entity = createEntity();
//            receptionResultRepository.save(entity);
//            if(isDBNotEmpty()){
//                receptionResultRepository.delete(returnsFirstInstanceFromDB().getId());
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
//            AppointmentRecordOldDB entity = createEntity();
//            receptionResultRepository.save(entity);
//            session.clear();
//            Long idForTestingMethod = returnsFirstInstanceFromDB().getId();
//            Optional<AppointmentRecordOldDB> mayBeEntity =  receptionResultRepository.findById(idForTestingMethod);
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
//            AppointmentRecordOldDB entity1 = createEntity();
//            receptionResultRepository.save(entity1);
//            AppointmentRecordOldDB entity2 = createEntity();
//            receptionResultRepository.save(entity2);
//            ArrayList<AppointmentRecordOldDB> entities = findAllEntities();
//            Assertions.assertEquals(2, entities.size());
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//    }
//
//    @Override
//    public AppointmentRecordOldDB createEntity() {
//        DoctorOldDB doctor = DoctorOldDB.builder()
//                .lastName("Шумилев")
//                .firstName("Алексей")
//                .middleName("Петрович")
//                .speciality(DoctorSpeciality.NEUROLOGIST)
//                .build();
//        doctorRepository.save(doctor);
//        PatientOldDB patient = PatientOldDB.builder()
//                .lastName("Шумилев")
//                .firstName("Алексей")
//                .middleName("Петрович")
//                .birthDate(LocalDate.of(1993, 3, 14))
//                .build();
//        patientRepository.save(patient);
//        DoctorOldDB doctorWithID = (DoctorOldDB) doctorRepository.findAll().get(0);
//        PatientOldDB patientWithID = (PatientOldDB) patientRepository.findAll().get(0);
//        AppointmentRecordOldDB receptionResult = AppointmentRecordOldDB.builder()
//                .doctor(doctorWithID)
//                .patient(patientWithID)
//                .receptionDate(LocalDate.of(2019, 5, 12))
//                .healthComplaints("Головные боли")
//                .doctorsRecommendation("Больше гулять на свежем воздухе")
//                .build();
//        return receptionResult;
//    }
//
//    @Override
//    public AppointmentRecordOldDB returnsFirstInstanceFromDB() {
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
//    public ArrayList<AppointmentRecordOldDB> findAllEntities() {
//        return receptionResultRepository.findAll();
//    }
//}