package service.patient;

import entity.Patient;
import exception.NotAuthenticatedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import repository.PatientRepository;
import service.Mapper;
import service.dto.patient.PatientDto;
import service.dto.patient.PatientLoginDto;
import util.ExceptionMessage;
import util.SessionPool;

@RequiredArgsConstructor
public class PatientLoginService {

    private final PatientRepository patientRepository;

    private final ObjectMapper objectMapper;

    private final Mapper mapper;

    @SneakyThrows
    public PatientDto authenticate(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        PatientLoginDto patientLoginDto = objectMapper.readValue(request.getInputStream(), PatientLoginDto.class);
        session.beginTransaction();
        try {
            Patient patient = patientRepository.authenticate(patientLoginDto.getLogin(), patientLoginDto.getPassword(), session)
                    .orElseThrow(() -> new NotAuthenticatedException(ExceptionMessage.NOT_AUTHENTICATED));
            PatientDto patientDto = mapper.mapToPatientDto(patient);
            session.getTransaction().commit();
            return patientDto;
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }
}
