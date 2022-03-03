package service.doctor;

import entity.newdb.DoctorNewDB;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.newdb.DoctorNewDBRepository;
import lombok.RequiredArgsConstructor;
import servlet.converter.request.DoctorLoginConverter;
import service.mapper.to.DoctorDtoMapper;
import service.dto.doctor.DoctorLoginDto;
import service.dto.doctor.DoctorDto;
import util.NewDBSessionPool;

import java.util.Optional;

@RequiredArgsConstructor
public class DoctorLoginService {
    private final Session session;

    private final DoctorNewDBRepository doctorRepository;

    private final DoctorLoginConverter doctorLoginConverter;
    private final DoctorDtoMapper doctorDtoMapper;

//    private final AppointmentRecordNewDBRepository appointmentRecordNewDBRepository;
//    private final AppointmentRecordOldDBRepository appointmentRecordOldDBRepository;
//    private final PatientOldDBRepository patientOldDBRepository;
//
//    private final DoctorRegistrationMapper doctorRegistrationMapper;
//    private final PatientFromOldDBToNewDBMapper patientFromOldDBToNewDBMapper;
//    private final ApRecordFromOldDBToNewDBMapper apRecordFromOldDBToNewDBMapper;

    public DoctorLoginService(){
        this.session = NewDBSessionPool.getSession();
        this.doctorRepository = new DoctorNewDBRepository(session);
        this.doctorLoginConverter = new DoctorLoginConverter();
        this.doctorDtoMapper = new DoctorDtoMapper();
    }

    @SneakyThrows
    public Optional<DoctorDto> authenticate(HttpServletRequest request) {
        DoctorLoginDto doctorLoginDto = doctorLoginConverter.convert(request);
        session.beginTransaction();
        Optional<DoctorNewDB> mayBeDoctor = doctorRepository.authenticate(doctorLoginDto.getLogin(), doctorLoginDto.getPassword());
        if (mayBeDoctor.isPresent()){
            DoctorDto doctorDto = doctorDtoMapper.mapFrom(mayBeDoctor.get());
            session.getTransaction().commit();
            return Optional.of(doctorDto);
        } else {
            session.getTransaction().rollback();
            return Optional.empty();
        }
    }

//    @Transactional
//    public String doctorRegistration (DoctorRegistrationDTO dto) {
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        Validator validator = validatorFactory.getValidator();
//        var validationResults = validator.validate(dto);
//        if (!validationResults.isEmpty()) {
//            throw new ConstraintViolationException(validationResults);
//        }
//        DoctorNewDB newDoctor = doctorRegistrationMapper.mapFrom(dto);
//        if (doctorNewDBRepository.findByLogin(newDoctor.getLogin()).isEmpty()){
//            doctorNewDBRepository.save(newDoctor);
//            return "Регистрация прошла успешно";
//        }else {
//            try {
//                throw new NotUniqueEntityException(DoctorNewDB.class.getName(), newDoctor.getLogin());
//            }catch (NotUniqueEntityException e){
//                return "Вы не зарегестрированы. Доктор с таким логином уже существует";
//            }
//        }
//    }

//    @Transactional
//    public ArrayList<PatientNewDB> findPatientByLastName (String lastName) throws PatientNotFoundException {
//        session.beginTransaction();
////        Optional<PatientNewDB> mayBePatientFromNewDB = patientNewDBRepository.findByFullNameAndBirthday(dto.getLastName(), dto.getFirstName(),
////                dto.getMiddleName(), dto.getBirthday());
//        ArrayList<PatientNewDB> patients = patientNewDBRepository.findByLastName(lastName);
//        session.getTransaction().commit();
//        if(patients.size() == 0){
//            throw new PatientNotFoundException(lastName);
//        }
//        return patients;
//    }

//    @Transactional
//    public String recordAnAppointment(DoctorNewDB doctor, ){
//        Optional<PatientNewDB> mayBePatientFromNewDB = patientNewDBRepository.findByFullNameAndBirthday(dto.getLastName(), dto.getFirstName(),
//                dto.getMiddleName(), dto.getBirthday());
//        if(mayBePatientFromNewDB.isPresent()){
//            PatientNewDB patient = mayBePatientFromNewDB.get();
//            AppointmentRecordDTO appointmentRecordDTO = AppointmentRecordDTO.builder()
//                    .patient(patient)
//                    .doctor(doctor)
//                    .visitDate()
//                    .build();
//            AppointmentRecordNewDB record = appointmentRecordMapper.mapFrom(appointmentRecordDTO);
//        }
//    }

//    @DoubleDBTransactional
//    public void savePatientAndHisRecordsFromOldDBInNewDB(PatientOldDB patientOldDB){
//
//        //ПРОВЕРКА ВСЕХ ДОКТОРОВ ИЗ СТАРОЙ И НОВОЙ БАЗ!!!!!!!!!!!!!!!!!
//
//        PatientNewDB patientNewDB = patientFromOldDBToNewDBMapper.mapFrom(patientOldDB);
//        patientNewDBRepository.save(patientNewDB);
//        LinkedList<AppointmentRecordOldDB> patientsOldDBRecords = (LinkedList<AppointmentRecordOldDB>) patientOldDB.getReceptionResults();
//        ArrayList<DoctorNewDB> allNewDBDoctors = doctorNewDBRepository.findAll();
//        LinkedList<AppointmentRecordNewDB> patientsNewDBRecords = new LinkedList<>();
//        for (AppointmentRecordOldDB recordOldDB : patientsOldDBRecords){
//            Optional<DoctorNewDB> mayBeDoctor = findDoctorFromOldDBInNewDB(allNewDBDoctors, recordOldDB.getDoctor());
//            mayBeDoctor.ifPresent(doctorNewDB -> patientsNewDBRecords.add(apRecordFromOldDBToNewDBMapper.mapFrom(recordOldDB, doctorNewDB, patientNewDB)));
//        }
//        for (AppointmentRecordNewDB recordNewDB : patientsNewDBRecords){
//              appointmentRecordNewDBRepository.save(recordNewDB);
//        }
//        for (AppointmentRecordOldDB recordOldDB : patientsOldDBRecords){
//            appointmentRecordOldDBRepository.delete(recordOldDB.getId());
//        }
//        patientOldDBRepository.delete(patientOldDB.getId());
//    }
//
////    @OldDBTransactional
//    private Optional<PatientOldDB> findPatientInOldDB(PatientFindDTO dto){
//        Optional<PatientOldDB> mayBePatientFromOldDB = patientOldDBRepository.findByFullNameAndBirthdate(dto.getLastName(), dto.getFirstName(),
//                dto.getMiddleName(), dto.getBirthday());
//        return mayBePatientFromOldDB;
//    }
//
//
//    private Optional<DoctorNewDB> findDoctorFromOldDBInNewDB(ArrayList<DoctorNewDB> newDBDoctors, DoctorOldDB doctorOldDB){
//        for (DoctorNewDB doctor : newDBDoctors) {
//            if (doctorOldDB.getLastName().equals(doctor.getLastName())
//                && doctorOldDB.getFirstName().equals(doctor.getFirstName())
//                && doctorOldDB.getSpeciality().equals(doctor.getSpeciality())){
//                    return Optional.of(doctor);
//            }
//        }
//        return Optional.empty();
//    }
}
