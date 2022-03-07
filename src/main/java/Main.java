//import entity.Doctor;
//import entity.DoctorSpeciality;
//import entity.Qualification;
//import org.hibernate.Session;
//import repository.DoctorRepository;
//import util.SessionPool;
//
//public class Main {
//    public static void main(String[] args) {
//        Session session = SessionPool.getSession();
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
//        doctorNewDBRepository.save(doctorNewDB, session);
//        session.getTransaction().commit();

//    }
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
