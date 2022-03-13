//import entity.Doctor;
//import entity.DoctorSpeciality;
//import entity.Qualification;
//import entity.WorkSchedule;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import repository.DoctorRepository;
//import util.HibernateUtil;
//
//import java.util.Optional;
//
//public class Main {
//    public static void main(String[] args) {
//        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        DoctorRepository doctorNewDBRepository = new DoctorRepository();
//        Doctor doctorNewDB = Doctor.builder()
//                .lastName("Админ")
//                .firstName("Админ")
//                .middleName("Админ")
//                .speciality(DoctorSpeciality.CHIEF_DOCTOR)
//                .qualification(Qualification.FIRST)
//                .login("admin")
//                .password("admin")
//                .build();
//        session.beginTransaction();
//        WorkSchedule schedule = WorkSchedule.builder()
//                .doctor(doctorNewDB)
//                .monday(Boolean.FALSE)
//                .tuesday(Boolean.FALSE)
//                .wednesday(Boolean.FALSE)
//                .thursday(Boolean.FALSE)
//                .friday(Boolean.FALSE)
//                .saturday(Boolean.FALSE)
//                .sunday(Boolean.FALSE)
//                .build();
////        session.save(doctorNewDB);
//
//
//        session.save(schedule);
//
//        session.getTransaction().commit();
//
//    }
//}
//        try (SessionFactory newDBSessionFactory = HibernateUtil.buildSessionFactoryForNEWdb();
//             SessionFactory oldDBSessionFactory = HibernateUtil.buildSessionFactoryForOLDdb()){
//
//            Session oldDBSession = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class}, (proxy, method, args1)
//                    -> method.invoke(oldDBSessionFactory.getCurrentSession(), args1));
//            Session newDBSession = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class}, (proxy, method, args1)
//                    -> method.invoke(newDBSessionFactory.getCurrentSession(), args1));
//
//
//            PatientNewDBRepository patientNewDBRepository = new PatientNewDBRepository(newDBSession);
//            AppointmentRecordNewDBRepository appointmentRecordNewDBRepository = new AppointmentRecordNewDBRepository(newDBSession);
//            AppointmentRecordOldDBRepository appointmentRecordOldDBRepository = new AppointmentRecordOldDBRepository(oldDBSession);
//            PatientOldDBRepository patientOldDBRepository = new PatientOldDBRepository(oldDBSession);
//
//            DoctorRegistrationMapper doctorRegistrationMapper = new DoctorRegistrationMapper();
//            AppointmentRecordMapper appointmentRecordMapper = new AppointmentRecordMapper();
//            PatientFromOldDBToNewDBMapper patientFromOldDBToNewDBMapper = new PatientFromOldDBToNewDBMapper();
//            ApRecordFromOldDBToNewDBMapper apRecordFromOldDBToNewDBMapper = new ApRecordFromOldDBToNewDBMapper();
//
//            TransactionalInterceptor transactionalInterceptor = new TransactionalInterceptor(newDBSessionFactory);
//            OldDBTransactionalInterceptor oldDBTransactionalInterceptor = new OldDBTransactionalInterceptor(oldDBSessionFactory);
//            DoubleDBTransactionalInterceptor doubleTransactionalInterceptor = new DoubleDBTransactionalInterceptor(oldDBSessionFactory, newDBSessionFactory);
//
//            DoctorService doctorService = null;
//            try {
//                doctorService = new ByteBuddy()
//                    .subclass(DoctorService.class)
//                    .method(ElementMatchers.any())
//                    .intercept(MethodDelegation.to(doubleTransactionalInterceptor))
//                    .make()
//                    .load(DoctorService.class.getClassLoader())
//                    .getLoaded()
//                    .getDeclaredConstructor(DoctorNewDBRepository.class, PatientNewDBRepository.class, AppointmentRecordNewDBRepository.class, AppointmentRecordOldDBRepository.class, DoctorRegistrationMapper.class, PatientOldDBRepository.class, PatientFromOldDBToNewDBMapper.class, ApRecordFromOldDBToNewDBMapper.class)
//                    .newInstance(doctorNewDBRepository, patientNewDBRepository, appointmentRecordNewDBRepository, appointmentRecordOldDBRepository, patientOldDBRepository, doctorRegistrationMapper, patientFromOldDBToNewDBMapper, apRecordFromOldDBToNewDBMapper);
//            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//
//            PatientOldDB patientOldDB = PatientOldDB.builder()
//                    .lastName("Иванов")
//                    .firstName("Александр")
//                    .middleName("Петрович")
//                    .birthDate(LocalDate.of(1994, 11, 18))
//                    .build();
//
//            doctorService.savePatientAndHisRecordsFromOldDBInNewDB(patientOldDB);

//        if (doctorNewDBRepository.authentication("Petrov", "petrov")){
//            System.out.println("TRUE");
//        }else System.out.println("FALSE");
//        }
//}
