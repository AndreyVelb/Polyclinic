//package integration.service.newdb.doctor;
//
//import dto.DoctorRegistrationDTO;
//import entity.DoctorSpeciality;
//import entity.Qualification;
//import integration.util.HibernateTestUtil;
//import service.mapper.dto.DoctorRegistrationMapper;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import repository.DoctorNewDBRepository;
//import service.DoctorService;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import java.util.Set;
//
//public class RegistrationTest {
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
//    void lastNameTest(){
//        session.beginTransaction();
//        DoctorRegistrationDTO rightLastName = createDto("lastName", "Петров");
//        DoctorRegistrationDTO nullLastName = createDto("lastName", null);
//        DoctorRegistrationDTO emptyLastName = createDto("lastName", "");
//        DoctorRegistrationDTO moreThenMaxCharLastName = createDto("lastName", "ААААААААААААААААААААААА");
//        DoctorRegistrationDTO nonCyrillicLastName = createDto("lastName", "Petrov");
//
//        Assertions.assertEquals("Регистрация прошла успешно", doctorService.doctorRegistration(rightLastName));
//        Assertions.assertEquals("Петров", repository.findAll().get(0).getLastName());
//
//        try{
//            doctorService.doctorRegistration(nullLastName);
//        }
//        catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ФАМИЛИЯ не должно быть пустым", handleConstraintViolationException(e));
//        }
//
//        try{
//            doctorService.doctorRegistration(nonCyrillicLastName);
//        }
//        catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ФАМИЛИЯ должно быть записано кирилическими буквами", handleConstraintViolationException(e));
//        }
//
//        try{
//            doctorService.doctorRegistration(emptyLastName);
//        }
//        catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ФАМИЛИЯ должно быть записано кирилическими буквами", handleConstraintViolationException(e));
//        }
//
//        try{
//            doctorService.doctorRegistration(moreThenMaxCharLastName);
//        }
//        catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ФАМИЛИЯ не должно превышать 20 символов", handleConstraintViolationException(e));
//        }
//
//        session.getTransaction().rollback();
//    }
//
//    @Test
//    void firstNameTest(){
//        session.beginTransaction();
//        DoctorRegistrationDTO rightFirstName = createDto("firstName", "Петр");
//        DoctorRegistrationDTO nullFirstName = createDto("firstName", null);
//        DoctorRegistrationDTO emptyFirstName = createDto("firstName", "");
//        DoctorRegistrationDTO moreThenMaxCharFirstName = createDto("firstName", "ААААААААААААААААААААААА");
//        DoctorRegistrationDTO nonCyrillicFirstName = createDto("firstName", "Petrov");
//
//        Assertions.assertEquals("Регистрация прошла успешно", doctorService.doctorRegistration(rightFirstName));
//        Assertions.assertEquals("Петр", repository.findAll().get(0).getFirstName());
//
//        try{
//            doctorService.doctorRegistration(nullFirstName);
//        }
//        catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ИМЯ не должно быть пустым", handleConstraintViolationException(e));
//        }
//
//        try{
//            doctorService.doctorRegistration(nonCyrillicFirstName);
//        }
//        catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ИМЯ должно быть записано кирилическими буквами", handleConstraintViolationException(e));
//        }
//
//        try{
//            doctorService.doctorRegistration(emptyFirstName);
//        }
//        catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ИМЯ должно быть записано кирилическими буквами", handleConstraintViolationException(e));
//        }
//
//        try{
//            doctorService.doctorRegistration(moreThenMaxCharFirstName);
//        }
//        catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ИМЯ не должно превышать 20 символов", handleConstraintViolationException(e));
//        }
//
//        session.getTransaction().rollback();
//    }
//
//    @Test
//    void middleNameTest(){
//        session.beginTransaction();
//        DoctorRegistrationDTO rightMiddleName = createDto("middleName", "Петрович");
//        DoctorRegistrationDTO nullMiddleName = createDto("middleName", null);
//        DoctorRegistrationDTO emptyMiddleName = createDto("middleName", "");
//        DoctorRegistrationDTO moreThenMaxCharMiddleName = createDto("middleName", "ААААААААААААААААААААААА");
//        DoctorRegistrationDTO nonCyrillicMiddleName = createDto("middleName", "Petrov");
//
//        Assertions.assertEquals("Регистрация прошла успешно", doctorService.doctorRegistration(rightMiddleName));
//        Assertions.assertEquals("Петрович", repository.findAll().get(0).getMiddleName());
//
//        Assertions.assertEquals("Регистрация прошла успешно",doctorService.doctorRegistration(nullMiddleName));
//        Assertions.assertNull(repository.findAll().get(1).getMiddleName());
//
//        Assertions.assertEquals("Регистрация прошла успешно", doctorService.doctorRegistration(emptyMiddleName));
//        Assertions.assertEquals("", repository.findAll().get(2).getMiddleName());
//
//        try{
//            doctorService.doctorRegistration(nonCyrillicMiddleName);
//        }
//        catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ОТЧЕСТВО должно быть либо пустым, либо быть записано киррилическими буквами", handleConstraintViolationException(e));
//        }
//
//        try{
//            doctorService.doctorRegistration(moreThenMaxCharMiddleName);
//        }
//        catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ОТЧЕСТВО не должно превышать 20 символов", handleConstraintViolationException(e));
//        }
//        session.getTransaction().rollback();
//    }
//
//    @Test
//    void loginTest(){
//        session.beginTransaction();
//        DoctorRegistrationDTO rightLogin = createDto("login", "login");
//        DoctorRegistrationDTO notUniqueLogin = createDto("login", "login");
//        DoctorRegistrationDTO nullLogin = createDto("login", null);
//        DoctorRegistrationDTO nonLatinLogin = createDto("login", "ЛОГИН");
//        DoctorRegistrationDTO emptyLogin = createDto("login", "");
//        DoctorRegistrationDTO moreThenMaxCharLogin = createDto("login", "LLLLLLLLLLLLLLLLLLLL");
//
//
//        Assertions.assertEquals("Регистрация прошла успешно", doctorService.doctorRegistration(rightLogin));
//        Assertions.assertEquals("login", repository.findAll().get(0).getLogin());
//        Assertions.assertEquals("Вы не зарегестрированы. Доктор с таким логином уже существует", doctorService.doctorRegistration(notUniqueLogin));
//
//        try {
//            doctorService.doctorRegistration(nullLogin);
//        }catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ЛОГИН не должно быть пустым", handleConstraintViolationException(e));
//        }
//
//        try {
//            doctorService.doctorRegistration(nonLatinLogin);
//        }catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ЛОГИН должно быть записано цифрами и(или) латинскими буквами", handleConstraintViolationException(e));
//        }
//
//        try {
//            doctorService.doctorRegistration(emptyLogin);
//        }catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ЛОГИН должно быть записано цифрами и(или) латинскими буквами", handleConstraintViolationException(e));
//        }
//
//        try {
//            doctorService.doctorRegistration(moreThenMaxCharLogin);
//        }catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ЛОГИН не должно превышать 30 символов", handleConstraintViolationException(e));
//        }
//
//        session.getTransaction().rollback();
//    }
//
//    @Test
//    void passwordTest(){
//        session.beginTransaction();
//        DoctorRegistrationDTO rightPassword = createDto("password", "password");
//        DoctorRegistrationDTO nullPassword = createDto("password", null);
//        DoctorRegistrationDTO nonLatinPassword = createDto("password", "ПАРОЛЬ");
//        DoctorRegistrationDTO emptyPassword = createDto("password", "");
//        DoctorRegistrationDTO moreThenMaxCharPassword = createDto("password", "LLLLLLLLLLLLLLLLLLLL");
//
//
//        Assertions.assertEquals("Регистрация прошла успешно", doctorService.doctorRegistration(rightPassword));
//        Assertions.assertEquals("password", repository.findAll().get(0).getPassword());
//
//        try {
//            doctorService.doctorRegistration(nullPassword);
//        }catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ПАРОЛЬ не должно быть пустым", handleConstraintViolationException(e));
//        }
//
//        try {
//            doctorService.doctorRegistration(nonLatinPassword);
//        }catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ПАРОЛЬ должно быть записано цифрами и(или) латинскими буквами", handleConstraintViolationException(e));
//        }
//
//        try {
//            doctorService.doctorRegistration(emptyPassword);
//        }catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ПАРОЛЬ должно быть записано цифрами и(или) латинскими буквами", handleConstraintViolationException(e));
//        }
//
//        try {
//            doctorService.doctorRegistration(moreThenMaxCharPassword);
//        }catch (ConstraintViolationException e){
//            Assertions.assertEquals("Поле ПАРОЛЬ не должно превышать 30 символов", handleConstraintViolationException(e));
//        }
//
//        session.getTransaction().rollback();
//    }
//
//    private DoctorRegistrationDTO createDto(String parameter, String value){
//        DoctorRegistrationDTO dto;
//        String defaultLastName = "Иванов";
//        String defaultFirstName = "Иван";
//        String defaultMiddleName = "Иванович";
//        DoctorSpeciality defaultSpeciality = DoctorSpeciality.GENERAL_DOCTOR;
//        Qualification defaultQualification = Qualification.FIRST;
//        String defaultLogin = "login" + (int) (Math.random() * 100000); // Math.random used to ensure the uniqueness of a login when creating several objects within a same test method
//        String defaultPassword = "password";
//        switch (parameter){
//            case "lastName" ->
//                    dto = DoctorRegistrationDTO.builder()
//                            .lastName(value)
//                            .firstName(defaultFirstName)
//                            .middleName(defaultMiddleName)
//                            .speciality(defaultSpeciality)
//                            .qualification(defaultQualification)
//                            .login(defaultLogin)
//                            .password(defaultPassword)
//                            .build();
//            case "firstName" ->
//                    dto = DoctorRegistrationDTO.builder()
//                            .lastName(defaultLastName)
//                            .firstName(value)
//                            .middleName(defaultMiddleName)
//                            .speciality(defaultSpeciality)
//                            .qualification(defaultQualification)
//                            .login(defaultLogin)
//                            .password(defaultPassword)
//                            .build();
//            case "middleName" ->
//                    dto = DoctorRegistrationDTO.builder()
//                            .lastName(defaultLastName)
//                            .firstName(defaultFirstName)
//                            .middleName(value)
//                            .speciality(defaultSpeciality)
//                            .qualification(defaultQualification)
//                            .login(defaultLogin)
//                            .password(defaultPassword)
//                            .build();
//            case "login" ->
//                    dto = DoctorRegistrationDTO.builder()
//                            .lastName(defaultLastName)
//                            .firstName(defaultFirstName)
//                            .middleName(defaultMiddleName)
//                            .speciality(defaultSpeciality)
//                            .qualification(defaultQualification)
//                            .login(value)
//                            .password(defaultPassword)
//                            .build();
//            case "password" ->
//                    dto = DoctorRegistrationDTO.builder()
//                            .lastName(defaultLastName)
//                            .firstName(defaultFirstName)
//                            .middleName(defaultMiddleName)
//                            .speciality(defaultSpeciality)
//                            .qualification(defaultQualification)
//                            .login(defaultLogin)
//                            .password(value)
//                            .build();
//
//            default -> throw new IllegalStateException("Unexpected value: " + parameter);
//        }
//        return dto;
//    }
//
//    private String handleConstraintViolationException(ConstraintViolationException e){
//        Set<ConstraintViolation<?>> validationResults = e.getConstraintViolations();
//        if (validationResults.size() == 1){
//            return validationResults.stream().findFirst().map(ConstraintViolation::getMessage).orElse("Ошибка c \"Set<ConstraintViolation<?>> validationResults\"");
//        }
//        else return "DTO содержит более одной ошибки валидации";
//    }
//}
