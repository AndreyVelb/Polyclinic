package service.admin;

import entity.DoctorsAppointment;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import repository.DoctorsAppointmentRepository;
import service.dto.admin.DocAppForAdminDto;
import service.mapper.DocAppDtoForAdminMapper;
import util.SessionPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class TimetableService {
    private final DoctorsAppointmentRepository doctorsAppointmentRepository;

    private final DocAppDtoForAdminMapper docAppointmentDtoMapper;

    public List<DocAppForAdminDto> getAllTimetable(){
        Session session = SessionPool.getSession();
        try {
            session.beginTransaction();
            List<DoctorsAppointment> timetable = doctorsAppointmentRepository.findAll(session);
            List<DocAppForAdminDto> timetableAsDto = new ArrayList<>();
            if (!timetable.isEmpty()){
                timetable.forEach(docApp -> timetableAsDto.add(docAppointmentDtoMapper.mapFrom(docApp)));
            }else return Collections.emptyList();
            session.getTransaction().commit();
            return timetableAsDto;
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }
}
