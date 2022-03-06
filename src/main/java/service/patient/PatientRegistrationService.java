package service.patient;

import exception.ServerTechnicalProblemsException;
import lombok.SneakyThrows;
import exception.AlreadyExistsException;
import exception.DtoValidationException;
import service.mapper.PatientDtoMapper;
import servlet.response.PatientRegistrationResponse;
import service.dto.patient.PatientDto;
import service.dto.patient.PatientRegistrationDto;
import entity.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.annotations.DynamicInsert;
import repository.PatientRepository;
import service.mapper.PatientMapper;
import servlet.converter.request.RegistrationPatientConverter;
import service.dto.validator.DtoValidator;
import util.SessionPool;

import java.io.IOException;
import java.io.PrintWriter;

import static jakarta.servlet.http.HttpServletResponse.*;

@DynamicInsert
public class PatientRegistrationService {
    private final Session session;

    private final PatientRepository patientNewDBRepository;

    private final RegistrationPatientConverter registrationPatientConverter;
    private final PatientMapper registrationPatientMapper;
    private final PatientDtoMapper patientDtoMapper;

    private final DtoValidator dtoValidator;

    public PatientRegistrationService(){
        this.session = SessionPool.getSession();
        this.patientNewDBRepository = new PatientRepository(session);
        this.registrationPatientConverter = new RegistrationPatientConverter();
        this.registrationPatientMapper = new PatientMapper();
        this.dtoValidator = new DtoValidator();
        this.patientDtoMapper = new PatientDtoMapper();
    }

    @SneakyThrows
    public void registration(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws AlreadyExistsException, IOException, ServerTechnicalProblemsException, DtoValidationException {
        PatientRegistrationResponse registrationResponse = new PatientRegistrationResponse();
        Patient patient = createPatient(request);
//            new ExceptionResponse().send(writer, response, exception, SC_UNSUPPORTED_MEDIA_TYPE);
//            new ExceptionResponse().send(writer, response, exception, SC_BAD_REQUEST);;

        session.beginTransaction();
        if(patientNewDBRepository.registerPatient(patient)){
            Patient registeredPatient = patientNewDBRepository.findByLogin(patient.getLogin()).get();
            PatientDto patientDto = patientDtoMapper.mapFrom(registeredPatient);
            session.getTransaction().commit();
            registrationResponse.send(response.getWriter(), response, patientDto, SC_CREATED);
        } else throw new AlreadyExistsException();
    }

    private Patient createPatient(HttpServletRequest request) throws IOException, DtoValidationException {
        Patient patient = new Patient();
        PatientRegistrationDto registrationDto = registrationPatientConverter.convert(request);
        if (dtoValidator.isValid(registrationDto)) {
            return registrationPatientMapper.mapFrom(registrationDto);
        }
        return patient;
    }
}