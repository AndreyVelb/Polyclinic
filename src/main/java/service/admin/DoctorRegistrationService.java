package service.admin;

import entity.Patient;
import exception.AlreadyExistsException;
import exception.DtoValidationException;
import exception.ServerTechnicalProblemsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorRepository;
import service.dto.patient.PatientDto;
import service.dto.patient.PatientRegistrationDto;
import service.dto.validator.DtoValidator;
import service.mapper.PatientDtoMapper;
import servlet.response.PatientRegistrationResponse;
import util.SessionPool;

import java.io.IOException;
import java.io.PrintWriter;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;

@RequiredArgsConstructor
public class DoctorRegistrationService {
    private final DoctorRepository doctorRepository;

    private final RegistrationDoctorConverter registrationDoctorConverter;
    private final DoctorMapper doctorMapper;
    private final PatientDtoMapper doctorDtoMapper;

    private final DtoValidator dtoValidator;

    @SneakyThrows
    public void registration(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws AlreadyExistsException, IOException, ServerTechnicalProblemsException, DtoValidationException {
        Session session = SessionPool.getSession();
        PatientRegistrationResponse registrationResponse = new PatientRegistrationResponse();
        Patient patient = createPatient(request);

        session.beginTransaction();
        if(doctorRepository.registerPatient(patient, session)){
            Patient registeredPatient = doctorRepository.findByLogin(patient.getLogin(), session).get();
            PatientDto patientDto = doctorDtoMapper.mapFrom(registeredPatient);
            session.getTransaction().commit();
            registrationResponse.send(response.getWriter(), response, patientDto, SC_CREATED);
        } else throw new AlreadyExistsException();
    }

    private Patient createPatient(HttpServletRequest request) throws IOException, DtoValidationException {
        Patient patient = new Patient();
        PatientRegistrationDto registrationDto = registrationDoctorConverter.convert(request);
        if (dtoValidator.isValid(registrationDto)) {
            return doctorMapper.mapFrom(registrationDto);
        }
        return patient;
    }
}
