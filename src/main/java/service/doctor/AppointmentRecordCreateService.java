package service.doctor;

import entity.AppointmentRecord;
import entity.Doctor;
import entity.Patient;
import exception.NotFoundException;
import exception.ServerTechnicalProblemsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.AppointmentRecordRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import service.dto.doctor.AppointmentRecordDto;
import service.dto.doctor.AppointmentRecordSavedDto;
import service.dto.doctor.AppointmentRecordRequestDto;
import service.dto.doctor.DoctorDto;
import service.dto.validator.DtoValidator;
import service.mapper.AppointmentRecordDtoMapper;
import service.mapper.AppointmentRecordMapper;
import service.mapper.DoctorDtoMapper;
import servlet.converter.request.AppointmentRecordRequestConverter;
import util.SessionPool;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class AppointmentRecordCreateService {

    private final PatientRepository patientRepository;
    private final AppointmentRecordRepository appointmentRecordRepository;
    private final DoctorRepository doctorRepository;

    private final AppointmentRecordRequestConverter appointmentRecordRequestConverter;
    private final AppointmentRecordMapper appointmentRecordMapper;
    private final AppointmentRecordDtoMapper appointmentRecordDtoMapper;

    private final DtoValidator dtoValidator;

    @SneakyThrows
    public AppointmentRecordDto writeAndSaveAppointmentRecord(HttpServletRequest request){
        Session session = SessionPool.getSession();
        try {
            DoctorDto doctorDto = (DoctorDto) request.getSession().getAttribute("DOCTOR");
            session.beginTransaction();
            Doctor doctor = doctorRepository.findById(doctorDto.getId(), SessionPool.getSession()).get();
            Long patientId = extractPatientIdFromRequest(request);
            Optional<Patient> mayBePatient = patientRepository.findById(patientId, session);
            if (mayBePatient.isPresent()) {
                Patient patient = mayBePatient.get();
                AppointmentRecord appointmentRecordWithoutId = createAppointmentRecord(doctor, patient, request);
                appointmentRecordRepository.save(appointmentRecordWithoutId, session);
                Optional<AppointmentRecord> appointmentRecordWithId = appointmentRecordRepository.findByDoctorPatientAndDate(appointmentRecordWithoutId, session);
                if (appointmentRecordWithId.isPresent()){
                    AppointmentRecordDto appointmentRecordDto = appointmentRecordDtoMapper.mapFrom(appointmentRecordWithId.get());
                    session.getTransaction().commit();
                    return appointmentRecordDto;
                }else throw new ServerTechnicalProblemsException();
            }else throw new NotFoundException();
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }

    @SneakyThrows
    private AppointmentRecord createAppointmentRecord(Doctor doctor, Patient patient, HttpServletRequest request){
        AppointmentRecordRequestDto appointmentRecordRequestDto = appointmentRecordRequestConverter.convert(request);
        AppointmentRecordSavedDto appointmentRecordSavedDto = AppointmentRecordSavedDto.builder()
                .doctor(doctor)
                .patient(patient)
                .visitDate(LocalDate.now())
                .healthComplaints(appointmentRecordRequestDto.getHealthComplaints())
                .doctorsRecommendation(appointmentRecordRequestDto.getDoctorsRecommendation())
                .build();
        dtoValidator.isValid(appointmentRecordSavedDto);
        return appointmentRecordMapper.mapFrom(appointmentRecordSavedDto);
    }

    private Long extractPatientIdFromRequest(HttpServletRequest request){
            String[] requestPathParts = request.getPathInfo().split("/");
            return Long.parseLong(requestPathParts[3]);       // 0-""/ 1-"doctor"/ 2-"patients"/ 3-"{some id}"/...
    }
}
