package service.doctor;

import entity.AppointmentRecord;
import entity.Doctor;
import entity.Patient;
import exception.NotFoundException;
import exception.PageNotFoundException;
import exception.ServerTechnicalProblemsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class AppointmentRecordService {

    private final AppointmentRecordRepository appointmentRecordRepository;

    private final AppointmentRecordDtoMapper appointmentRecordDtoMapper;

    @SneakyThrows
    public AppointmentRecordDto getAppointmentRecordDto(HttpServletRequest request){
        Session session = SessionPool.getSession();
        Long appRecordId = extractAppointmentRecordIdFromRequest(request);
        try {
            session.beginTransaction();
            Optional<AppointmentRecord> mayBeAppointmentRecord = appointmentRecordRepository.findById(appRecordId, session);
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
        return Long.parseLong(requestPathParts[6]);  // 0-""/ 1-"doctor"/ 2-{id}/ 3-"patients"/ 4-{id}/ 5-"records/ 6-"{id}"
    }
}
