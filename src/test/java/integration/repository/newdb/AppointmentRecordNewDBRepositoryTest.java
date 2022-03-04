//package integration.repository.newdb;
//
//import entity.DoctorSpeciality;
//import entity.AppointmentRecordNewDB;
//import entity.DoctorNewDB;
//import entity.PatientNewDB;
//import entity.Qualification;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import repository.AppointmentRecordNewDBRepository;
//import repository.DoctorNewDBRepository;
//import repository.PatientNewDBRepository;
//import integration.util.HibernateTestUtil;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Optional;
//
//class AppointmentRecordNewDBRepositoryTest implements NEWdbRepositoryTest<Long, AppointmentRecordNewDB>{
//    private static SessionFactory sessionFactory;
//    private static Session session;
//    private static DoctorNewDBRepository doctorRepository;
//    private static PatientNewDBRepository patientRepository;
//    private static AppointmentRecordNewDBRepository appointmentRecordRepository;
//
//    @BeforeAll
//    static void open(){
//        sessionFactory = HibernateTestUtil.buildTestSessionFactoryForNEWdb();
//        session = sessionFactory.openSession();
//        doctorRepository = new DoctorNewDBRepository(session);
//        patientRepository = new PatientNewDBRepository(session);
//        appointmentRecordRepository = new AppointmentRecordNewDBRepository(session);
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
//            AppointmentRecordNewDB entity = createEntity();
//            appointmentRecordRepository.save(entity);
//            Assertions.assertTrue(isDBNotEmpty());
//            session.flush();
//            session.clear();
//            entity.setHealthComplaints("Боль в животе");
//            appointmentRecordRepository.update(entity);
//            session.flush();
//            session.clear();
//            AppointmentRecordNewDB mayBeUpdatingEntity = returnsFirstInstanceFromDB();
//            if(isDBNotEmpty()){
//                Assertions.assertEquals("Боль в животе", mayBeUpdatingEntity.getHealthComplaints());
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
//            AppointmentRecordNewDB entity = createEntity();
//            appointmentRecordRepository.save(entity);
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
//            AppointmentRecordNewDB entity = createEntity();
//            appointmentRecordRepository.save(entity);
//            if(isDBNotEmpty()){
//                appointmentRecordRepository.delete(returnsFirstInstanceFromDB().getId());
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
//            AppointmentRecordNewDB entity = createEntity();
//            appointmentRecordRepository.save(entity);
//            session.clear();
//            Long idForTestingMethod = returnsFirstInstanceFromDB().getId();
//            Optional<AppointmentRecordNewDB> mayBeEntity =  appointmentRecordRepository.findById(idForTestingMethod);
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
//            AppointmentRecordNewDB entity1 = createEntity();
//            appointmentRecordRepository.save(entity1);
//            AppointmentRecordNewDB entity2 = createEntity();
//            appointmentRecordRepository.save(entity2);
//            ArrayList<AppointmentRecordNewDB> entities = findAllEntities();
//            Assertions.assertEquals(2, entities.size());
//        }
//        else Assertions.fail();
//        session.getTransaction().rollback();
//    }
//
//    @Override
//    public AppointmentRecordNewDB createEntity() {
//        DoctorNewDB doctor = DoctorNewDB.builder()
//                .lastName("Иванов")
//                .firstName("Иван")
//                .middleName("Иванович")
//                .qualification(Qualification.FIRST)
//                .speciality(DoctorSpeciality.NEUROLOGIST)
//                .login("IvanovII")
//                .password("ivanov")
//                .build();
//        PatientNewDB patient = PatientNewDB.builder()
//                .lastName("Сидоров")
//                .firstName("Игорь")
//                .middleName("Игоревич")
//                .birthDate(LocalDate.of(1997, 11, 14))
//                .build();
//        doctorRepository.save(doctor);
//        patientRepository.save(patient);
//        DoctorNewDB doctorWithID = doctorRepository.findAll().get(0);
//        PatientNewDB patientWithID = patientRepository.findAll().get(0);
//        AppointmentRecordNewDB appointmentRecord = AppointmentRecordNewDB.builder()
//                .doctor(doctorWithID)
//                .patient(patientWithID)
//                .visitDate(LocalDate.of(2020, 11,15))
//                .healthComplaints("Головная боль")
//                .doctorsRecommendation("Больше бывать на свежем воздухе")
//                .build();
//        return appointmentRecord;
//    }
//
//    @Override
//    public AppointmentRecordNewDB returnsFirstInstanceFromDB() {
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
//    public ArrayList<AppointmentRecordNewDB> findAllEntities() {
//        return appointmentRecordRepository.findAll();
//    }
//}