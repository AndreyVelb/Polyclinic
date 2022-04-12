package service.patient;

import config.Config;
import entity.DoctorsAppointment;
import entity.Patient;
import exception.DocAppointmentNotFoundException;
import exception.NotFoundException;
import exception.PatientNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorsAppointmentRepository;
import repository.PatientRepository;
import service.Mapper;
import service.dto.patient.DocAppForPatientDto;
import util.SessionPool;

@RequiredArgsConstructor
public class BookingDoctorsAppointmentService {

    private final Config config;

    private final DoctorsAppointmentRepository doctorsAppointmentRepository;
    private final PatientRepository patientRepository;

    private final Mapper mapper;

    @SneakyThrows
    public DocAppForPatientDto getDoctorsAppointment(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        Long docAppId = extractDoctorsAppointmentIdFromRequest(request);
        try {
            session.beginTransaction();
            DoctorsAppointment doctorAppointment = doctorsAppointmentRepository.findById(docAppId, session)
                    .orElseThrow(() -> new NotFoundException(config.getNotFoundExMessage()));
            session.getTransaction().commit();
            return mapper.mapToDocAppForPatientDto(doctorAppointment);
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }

    @SneakyThrows
    public void bookDoctorsAppointment(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        Long patientId = extractPatientIdFromRequest(request);
        Long docAppId = extractDoctorsAppointmentIdFromRequest(request);
        try {
            session.beginTransaction();
            Patient patient = (patientRepository.findById(patientId, session))
                    .orElseThrow(() -> new PatientNotFoundException(config.getPatientNotFoundExMessage()));
            DoctorsAppointment doctorsAppointment = doctorsAppointmentRepository.findById(docAppId, session)
                    .orElseThrow(() -> new DocAppointmentNotFoundException(config.getDocAppointmentNotFoundExMessage()));
            doctorsAppointmentRepository.bookDoctorsAppointment(doctorsAppointment, patient, session);
            session.getTransaction().commit();
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }

    private Long extractDoctorsAppointmentIdFromRequest(HttpServletRequest request) {
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[6]);       // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-{id}/ 5-"appointments"/ 6-{id}
    }

    private Long extractPatientIdFromRequest(HttpServletRequest request) {
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[2]);       // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-{id}/ 5-"appointments"/ 6-{id}
    }
}
