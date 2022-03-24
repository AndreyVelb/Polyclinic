package service.patient;

import entity.DoctorsAppointment;
import entity.Patient;
import exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorsAppointmentRepository;
import repository.PatientRepository;
import service.dto.patient.DocAppForPatientDto;
import service.mapper.DocAppDtoForPatientMapper;
import util.SessionPool;

import java.util.Optional;

@RequiredArgsConstructor
public class BookingDoctorsAppointmentService {
    private final DoctorsAppointmentRepository doctorsAppointmentRepository;
    private final PatientRepository patientRepository;

    private final DocAppDtoForPatientMapper docAppDtoMapper;

    @SneakyThrows
    public DocAppForPatientDto getDoctorsAppointment(HttpServletRequest request){
        Session session = SessionPool.getSession();
        Long docAppId = extractDoctorsAppointmentIdFromRequest(request);
        try {
            session.beginTransaction();
            Optional<DoctorsAppointment> mayBeDocApp = doctorsAppointmentRepository.findById(docAppId, session);
            session.getTransaction().commit();
            if (mayBeDocApp.isPresent()){
                return docAppDtoMapper.mapFrom(mayBeDocApp.get());
            } else {
                session.getTransaction().rollback();
                throw new NotFoundException();
            }
        } catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }

    @SneakyThrows
    public void bookDoctorsAppointment(HttpServletRequest request){
        Session session = SessionPool.getSession();
        Long patientId = extractPatientIdFromRequest(request);
        Long docAppId = extractDoctorsAppointmentIdFromRequest(request);
        try {
            session.beginTransaction();
            Patient patient = (patientRepository.findById(patientId, session)).get();
            doctorsAppointmentRepository.bookDoctorsAppointment(docAppId, patient, session);
            session.getTransaction().commit();
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }

    private Long extractDoctorsAppointmentIdFromRequest(HttpServletRequest request){
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[6]);       // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-{id}/ 5-"appointments"/ 6-{id}
    }

    private Long extractPatientIdFromRequest(HttpServletRequest request){
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[2]);       // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-{id}/ 5-"appointments"/ 6-{id}
    }
}
