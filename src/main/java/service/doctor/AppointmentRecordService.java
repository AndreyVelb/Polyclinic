package service.doctor;

import entity.AppointmentRecord;
import exception.PageNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.AppointmentRecordRepository;
import service.Mapper;
import service.dto.doctor.AppointmentRecordDto;
import util.SessionPool;

import java.util.Optional;

@RequiredArgsConstructor
public class AppointmentRecordService {

    private final AppointmentRecordRepository appointmentRecordRepository;

    private final Mapper mapper;

    @SneakyThrows
    public AppointmentRecordDto getAppointmentRecordDto(HttpServletRequest request){
        Session session = SessionPool.getSession();
        Long appRecordId = extractAppointmentRecordIdFromRequest(request);
        session.beginTransaction();
        try {
            AppointmentRecord appointmentRecord = appointmentRecordRepository.findById(appRecordId, session).orElseThrow(PageNotFoundException::new);
            return mapper.mapToAppRecordDto(appointmentRecord);
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }

    private Long extractAppointmentRecordIdFromRequest(HttpServletRequest request){
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[6]);     // 0-""/ 1-"doctor"/ 2-{id}/ 3-"patients"/ 4-{id}/ 5-"records/ 6-"{id}"
    }
}
