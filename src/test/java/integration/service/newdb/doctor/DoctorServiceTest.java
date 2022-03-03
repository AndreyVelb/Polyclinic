package integration.service.newdb.doctor;

//class DoctorServiceTest {
//    private static SessionFactory sessionFactory;
//    private static Session session;
//    private static DoctorNewDBRepository repository;
//    private static DoctorRegistrationMapper doctorRegistrationMapper;
//    private static DoctorService doctorService;
//
//    @BeforeAll
//    public static void open(){
//        sessionFactory = HibernateTestUtil.buildTestSessionFactoryForNEWdb();
//        session = sessionFactory.openSession();
//        repository = new DoctorNewDBRepository(session);
//        doctorRegistrationMapper = new DoctorRegistrationMapper();
////        doctorService = new DoctorService(repository, doctorRegistrationMapper);
//    }
//
//    @AfterAll
//    public static void close(){
//        session.close();
//        sessionFactory.close();
//    }
//
//    @Test
//    void doctorAuthentication() {
//    }
//
//    @Test
//    void doctorRegistration() {
//        DoctorRegistrationDTO rightDto = DoctorRegistrationDTO.builder()
//                .lastName("Иванов")
//                .firstName("Иван")
//                .middleName("Иванович")
//                .speciality(DoctorSpeciality.SURGEON)
//                .qualification(Qualification.FIFTH)
//                .login("login")
//                .password("password")
//                .build();
////        String result = doctorService.doctorRegistration(rightDto);
////        Assertions.assertEquals("Регистрация прошла успешно", result);
//    }
//
//    @Test
//    void recordAnAppointment() {
//    }
//}