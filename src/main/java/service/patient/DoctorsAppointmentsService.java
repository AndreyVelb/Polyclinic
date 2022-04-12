package service.patient;

import entity.DoctorsAppointment;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorsAppointmentRepository;
import service.Mapper;
import service.dto.patient.DocAppForPatientDto;
import util.SessionPool;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DoctorsAppointmentsService {

    private final DoctorsAppointmentRepository doctorsAppointmentRepository;

    private final Mapper mapper;

    @SneakyThrows
    public ArrayList<DocAppForPatientDto> getVacantDoctorsAppointments(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        Long doctorId = extractDoctorIdFromRequest(request);
        List<DoctorsAppointment> doctorsAppointments;
        try {
            session.beginTransaction();
            doctorsAppointments = doctorsAppointmentRepository.getVacantAppointmentsAtDoctor(doctorId, session);
            session.getTransaction().commit();
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
        ArrayList<DocAppForPatientDto> doctorsAppointmentsDtoList = new ArrayList<>();
        doctorsAppointments.forEach(doctorsAppointment -> doctorsAppointmentsDtoList.add(mapper.mapToDocAppForPatientDto(doctorsAppointment)));
        return doctorsAppointmentsDtoList;
    }

    private Long extractDoctorIdFromRequest(HttpServletRequest request) {
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[4]);       // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-{id}/...
    }
}
