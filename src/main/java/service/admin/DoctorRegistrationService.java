package service.admin;

import entity.Doctor;
import entity.WorkSchedule;
import exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import repository.DoctorRepository;
import repository.WorkScheduleRepository;
import service.Mapper;
import service.dto.admin.DoctorRegistrationDto;
import service.dto.validator.DtoValidator;
import util.SessionPool;

@RequiredArgsConstructor
public class DoctorRegistrationService {
    private final DoctorRepository doctorRepository;
    private final WorkScheduleRepository workScheduleRepository;

    private final ObjectMapper objectMapper;

    private final Mapper mapper;

    private final DtoValidator dtoValidator;

    @SneakyThrows
    public Long registration(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        DoctorRegistrationDto doctorRegistrationDto = objectMapper.readValue(request.getInputStream(), DoctorRegistrationDto.class);
        dtoValidator.validate(doctorRegistrationDto);
        Doctor doctor = mapper.mapToDoctor(doctorRegistrationDto);
        WorkSchedule workSchedule = doctorRegistrationDto.getSchedule();
        workSchedule.setDoctor(doctor);
        try {
            session.beginTransaction();
            if (doctorRepository.findByLogin(doctor.getLogin(), session).isEmpty()){
                Long doctorId = doctorRepository.registerDoctor(doctor, session);
                workScheduleRepository.save(workSchedule, session);
                session.getTransaction().commit();
                return doctorId;
            } else {
                throw new UserAlreadyExistsException();
            }
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }

}
