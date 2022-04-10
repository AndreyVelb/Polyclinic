package service.admin;

import entity.DoctorsAppointment;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import repository.DoctorsAppointmentRepository;
import service.Mapper;
import service.dto.admin.DocAppForAdminDto;
import util.SessionPool;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TimetableService {
    private final DoctorsAppointmentRepository doctorsAppointmentRepository;

    private final Mapper mapper;

    public List<DocAppForAdminDto> getAllTimetable(){
        Session session = SessionPool.getSession();
        try {
            session.beginTransaction();
            List<DoctorsAppointment> timetable = doctorsAppointmentRepository.findAll(session);
            List<DocAppForAdminDto> timetableAsDto = new ArrayList<>();
            timetable.forEach(docApp -> timetableAsDto.add(mapper.mapToDocAppForAdminDto(docApp)));
            session.getTransaction().commit();
            return timetableAsDto;
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }
}
