package service.admin;

import entity.Doctor;
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
import service.dto.admin.DoctorRegistrationDto;
import service.dto.doctor.DoctorDto;
import service.dto.patient.PatientDto;
import service.dto.patient.PatientRegistrationDto;
import service.dto.validator.DtoValidator;
import service.mapper.DoctorDtoMapper;
import service.mapper.DoctorMapper;
import service.mapper.PatientDtoMapper;
import servlet.converter.request.RegistrationDoctorConverter;
import servlet.response.PatientRegistrationResponse;
import util.SessionPool;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;

@RequiredArgsConstructor
public class DoctorRegistrationService {
    private final DoctorRepository doctorRepository;

    private final RegistrationDoctorConverter registrationDoctorConverter;
    private final DoctorMapper doctorMapper;
    private final DoctorDtoMapper doctorDtoMapper;

    private final DtoValidator dtoValidator;

    @SneakyThrows
    public DoctorDto registration(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        Doctor doctor = createDoctor(request);
        try {
            session.beginTransaction();
            if(doctorRepository.registerDoctor(doctor, session)) {
                Doctor registeredDoctor = doctorRepository.findByLogin(doctor.getLogin(), session).get();
                session.getTransaction().commit();
                return doctorDtoMapper.mapFrom(registeredDoctor);
            }else throw new AlreadyExistsException();
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }

    @SneakyThrows
    private Doctor createDoctor(HttpServletRequest request) {
        DoctorRegistrationDto registrationDto = registrationDoctorConverter.convert(request);
        try {
            dtoValidator.isValid(registrationDto);
            return doctorMapper.mapFrom(registrationDto);

        }catch (ConstraintViolationException exception){
            throw new DtoValidationException(exception);
        }
    }
}
