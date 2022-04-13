package service.doctor;

import entity.Doctor;
import exception.NotAuthenticatedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import repository.DoctorRepository;
import service.Mapper;
import service.dto.doctor.DoctorDto;
import service.dto.doctor.DoctorLoginDto;
import util.ExceptionMessage;
import util.SessionPool;

@RequiredArgsConstructor
public class DoctorLoginService {

    private final DoctorRepository doctorRepository;

    private final ObjectMapper objectMapper;

    private final Mapper mapper;

    @SneakyThrows
    public DoctorDto authenticate(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        DoctorLoginDto doctorLoginDto = objectMapper.readValue(request.getInputStream(), DoctorLoginDto.class);
        session.beginTransaction();
        try {
            Doctor doctor = doctorRepository.authenticate(doctorLoginDto.getLogin(), doctorLoginDto.getPassword(), session)
                    .orElseThrow(() -> new NotAuthenticatedException(ExceptionMessage.NOT_AUTHENTICATED));
            session.getTransaction().commit();
            return mapper.mapToDoctorDto(doctor);
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }
}
