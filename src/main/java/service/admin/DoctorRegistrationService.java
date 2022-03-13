package service.admin;

import entity.Doctor;
import entity.WorkSchedule;
import exception.AlreadyExistsException;
import exception.DtoValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorRepository;
import repository.WorkScheduleRepository;
import service.dto.admin.DoctorRegistrationDto;
import service.dto.doctor.DoctorDto;
import service.dto.validator.DtoValidator;
import service.mapper.DoctorDtoMapper;
import service.mapper.DoctorMapper;
import servlet.converter.request.RegistrationDoctorConverter;
import util.SessionPool;

import javax.validation.ConstraintViolationException;

@RequiredArgsConstructor
public class DoctorRegistrationService {
    private final DoctorRepository doctorRepository;
    private final WorkScheduleRepository workScheduleRepository;

    private final RegistrationDoctorConverter registrationDoctorConverter;
    private final DoctorMapper doctorMapper;
    private final DoctorDtoMapper doctorDtoMapper;

    private final DtoValidator dtoValidator;

    @SneakyThrows
    public DoctorDto registration(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        DoctorRegistrationDto doctorRegistrationDto = registrationDoctorConverter.convert(request);
        Doctor doctor = createDoctor(doctorRegistrationDto);
        WorkSchedule workSchedule = createSchedule(doctorRegistrationDto, doctor);
        try {
            session.beginTransaction();

            if( doctorRepository.registerDoctor(doctor, session)) {
                workScheduleRepository.save(workSchedule, session);
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
    private Doctor createDoctor(DoctorRegistrationDto registrationDto) {
        try {
            dtoValidator.isValid(registrationDto);
            return doctorMapper.mapFrom(registrationDto);

        }catch (ConstraintViolationException exception){
            throw new DtoValidationException(exception);
        }
    }

    private WorkSchedule createSchedule(DoctorRegistrationDto registrationDto, Doctor doctor){
        WorkSchedule workSchedule = registrationDto.getSchedule();
        workSchedule.setDoctor(doctor);
        return workSchedule;
    }
}
