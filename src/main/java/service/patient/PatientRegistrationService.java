package service.patient;

import config.Config;
import entity.Patient;
import exception.PatientNotFoundException;
import exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import repository.PatientRepository;
import service.Mapper;
import service.dto.patient.PatientDto;
import service.dto.patient.PatientRegistrationDto;
import service.dto.validator.DtoValidator;
import util.SessionPool;

@RequiredArgsConstructor
public class PatientRegistrationService {

    private final Config config;

    private final PatientRepository patientRepository;

    private final ObjectMapper objectMapper;

    private final Mapper mapper;

    private final DtoValidator dtoValidator;

    @SneakyThrows
    public PatientDto registration(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        Patient patient = createPatient(request);
        try {
            session.beginTransaction();
            if (patientRepository.findByLogin(patient.getLogin(), session).isEmpty()) {
                patientRepository.save(patient, session);
                Patient registeredPatient = patientRepository.findByLogin(patient.getLogin(), session)
                        .orElseThrow(() -> new PatientNotFoundException(config.getPatientNotFoundExMessage()));
                session.getTransaction().commit();
                return mapper.mapToPatientDto(registeredPatient);
            } else throw new UserAlreadyExistsException();
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }

    @SneakyThrows
    private Patient createPatient(HttpServletRequest request) {
        PatientRegistrationDto registrationDto = objectMapper.readValue(request.getInputStream(), PatientRegistrationDto.class);
        dtoValidator.validate(registrationDto);
        return mapper.mapToPatient(registrationDto);
    }
}