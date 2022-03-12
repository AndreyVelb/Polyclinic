package service.admin;

import entity.DoctorsAppointment;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import repository.DoctorsAppointmentRepository;
import service.dto.admin.DoctorsAppointmentForAdminDto;
import service.mapper.DoctorsAppointmentDtoMapper;
import util.SessionPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class TimetableService {
    private final DoctorsAppointmentRepository doctorsAppointmentRepository;

    private final DoctorsAppointmentDtoMapper docAppointmentDtoMapper;

    public List<DoctorsAppointmentForAdminDto> getAllTimetable(){
        Session session = SessionPool.getSession();
        try {
            session.beginTransaction();
            List<DoctorsAppointment> timetable = doctorsAppointmentRepository.findAll(session);
            List<DoctorsAppointmentForAdminDto> timetableAsDto = new ArrayList<>();
            if (!timetable.isEmpty()){
                timetable.forEach(docApp -> timetableAsDto.add(docAppointmentDtoMapper.mapFrom(docApp)));
            }else return Collections.emptyList();
            return timetableAsDto;
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }
}
