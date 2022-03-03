package service.patient;

import exception.ServerTechnicalProblemsException;
import lombok.SneakyThrows;
import exception.AlreadyExistsException;
import exception.DtoValidationException;
import service.mapper.to.PatientDtoMapper;
import servlet.response.PatientRegistrationResponse;
import service.dto.patient.PatientDto;
import service.dto.patient.PatientRegistrationDto;
import entity.newdb.PatientNewDB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.annotations.DynamicInsert;
import repository.newdb.PatientNewDBRepository;
import service.mapper.from.dto.RegistrationPatientMapper;
import servlet.converter.request.RegistrationPatientConverter;
import service.dto.validator.DtoValidator;
import util.NewDBSessionPool;

import java.io.IOException;
import java.io.PrintWriter;

import static jakarta.servlet.http.HttpServletResponse.*;

@DynamicInsert
public class PatientRegistrationService {
    private final Session session;

    private final PatientNewDBRepository patientNewDBRepository;

    private final RegistrationPatientConverter registrationPatientConverter;
    private final RegistrationPatientMapper registrationPatientMapper;
    private final PatientDtoMapper patientDtoMapper;

    private final DtoValidator dtoValidator;

    public PatientRegistrationService(){
        this.session = NewDBSessionPool.getSession();
        this.patientNewDBRepository = new PatientNewDBRepository(session);
        this.registrationPatientConverter = new RegistrationPatientConverter();
        this.registrationPatientMapper = new RegistrationPatientMapper();
        this.dtoValidator = new DtoValidator();
        this.patientDtoMapper = new PatientDtoMapper();
    }

    @SneakyThrows
    public void registration(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws AlreadyExistsException, IOException, ServerTechnicalProblemsException, DtoValidationException {
        PatientRegistrationResponse registrationResponse = new PatientRegistrationResponse();
        PatientNewDB patient = createPatient(request);
//            new ExceptionResponse().send(writer, response, exception, SC_UNSUPPORTED_MEDIA_TYPE);
//            new ExceptionResponse().send(writer, response, exception, SC_BAD_REQUEST);;

        session.beginTransaction();
        if(patientNewDBRepository.registerPatient(patient)){
            PatientNewDB registeredPatient = patientNewDBRepository.findByLogin(patient.getLogin()).get();
            PatientDto patientDto = patientDtoMapper.mapFrom(registeredPatient);
            session.getTransaction().commit();
            registrationResponse.send(response.getWriter(), response, patientDto, SC_CREATED);
        } else throw new AlreadyExistsException();
    }

    private PatientNewDB createPatient(HttpServletRequest request) throws IOException, DtoValidationException {
        PatientNewDB patient = new PatientNewDB();
        PatientRegistrationDto registrationDto = registrationPatientConverter.convert(request);
        if (dtoValidator.isValid(registrationDto)) {
            return registrationPatientMapper.mapFrom(registrationDto);
        }
        return patient;
    }
}