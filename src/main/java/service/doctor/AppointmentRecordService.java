package service.doctor;

import entity.AppointmentRecord;
import entity.Doctor;
import entity.Patient;
import exception.NotFoundException;
import exception.PageNotFoundException;
import exception.ServerTechnicalProblemsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.AppointmentRecordRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import service.dto.doctor.AppointmentRecordDto;
import service.dto.doctor.AppointmentRecordRequestDto;
import service.dto.doctor.AppointmentRecordSavedDto;
import service.dto.doctor.DoctorDto;
import service.mapper.AppointmentRecordDtoMapper;
import service.mapper.AppointmentRecordMapper;
import service.mapper.DoctorDtoMapper;
import servlet.converter.request.AppointmentRecordRequestConverter;
import util.SessionPool;

import java.time.LocalDate;
import java.util.Optional;

public class AppointmentRecordService {
    private final Session session;

    private final PatientRepository patientRepository;
    private final AppointmentRecordRepository appointmentRecordRepository;
    private final DoctorRepository doctorRepository;

    private final AppointmentRecordRequestConverter appointmentRecordConverter;
    private final DoctorDtoMapper doctorDtoMapper;
    private final AppointmentRecordMapper appointmentRecordMapper;
    private final AppointmentRecordDtoMapper appointmentRecordDtoMapper;

    public AppointmentRecordService(){
        this.session = SessionPool.getSession();
        this.patientRepository = new PatientRepository(session);
        this.doctorRepository = new DoctorRepository(session);
        this.appointmentRecordRepository = new AppointmentRecordRepository(session);
        this.appointmentRecordConverter = new AppointmentRecordRequestConverter();
        this.doctorDtoMapper = new DoctorDtoMapper();
        this.appointmentRecordMapper = new AppointmentRecordMapper();
        this.appointmentRecordDtoMapper = new AppointmentRecordDtoMapper();
    }

    @SneakyThrows
    public AppointmentRecordDto getAppointmentRecordDto(HttpServletRequest request){
        Long appRecordId = extractAppointmentRecordIdFromRequest(request);
        try {
            session.beginTransaction();
            Optional<AppointmentRecord> mayBeAppointmentRecord = appointmentRecordRepository.findById(appRecordId);
            if (mayBeAppointmentRecord.isPresent()){
                return appointmentRecordDtoMapper.mapFrom(mayBeAppointmentRecord.get());
            }else throw new PageNotFoundException();
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }

    private Long extractAppointmentRecordIdFromRequest(HttpServletRequest request){
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[5]);  // 0-""/ 1-"doctor"/ 2-"patients"/ 3-"{some id}"/ 4-"records/ 5-"{some record-id}"
    }
}
