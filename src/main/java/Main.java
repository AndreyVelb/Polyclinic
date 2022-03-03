import entity.DoctorSpeciality;
import entity.newdb.DoctorNewDB;
import entity.newdb.Qualification;
import org.hibernate.Session;
import repository.newdb.DoctorNewDBRepository;
import util.HibernateUtil;
import util.NewDBSessionPool;

//public class Main {
//    public static void main(String[] args) {
//        Session session = NewDBSessionPool.getSession();
//        DoctorNewDBRepository doctorNewDBRepository = new DoctorNewDBRepository(session);
//        DoctorNewDB doctorNewDB = DoctorNewDB.builder()
//                .lastName("Иванов")
//                .firstName("Иван")
//                .middleName("Иванович")
//                .speciality(DoctorSpeciality.SURGEON)
//                .qualification(Qualification.FIRST)
//                .login("ivanov")
//                .password("ivanov")
//                .build();
//        session.beginTransaction();
//        doctorNewDBRepository.save(doctorNewDB);
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
//
////        if (doctorNewDBRepository.authentication("Petrov", "petrov")){
////            System.out.println("TRUE");
////        }else System.out.println("FALSE");
//        }

//    }
//}
