package service.doctor;

import entity.Doctor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import servlet.converter.request.DoctorLoginConverter;
import service.mapper.DoctorDtoMapper;
import service.dto.doctor.DoctorLoginDto;
import service.dto.doctor.DoctorDto;
import util.SessionPool;

import java.util.Optional;

@RequiredArgsConstructor
public class DoctorLoginService {

    private final DoctorRepository doctorRepository;

    private final DoctorLoginConverter doctorLoginConverter;
    private final DoctorDtoMapper doctorDtoMapper;

    @SneakyThrows
    public Optional<DoctorDto> authenticate(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        DoctorLoginDto doctorLoginDto = doctorLoginConverter.convert(request);
        session.beginTransaction();
        Optional<Doctor> mayBeDoctor = doctorRepository.authenticate(doctorLoginDto.getLogin(), doctorLoginDto.getPassword(), session);
        if (mayBeDoctor.isPresent()){
            DoctorDto doctorDto = doctorDtoMapper.mapFrom(mayBeDoctor.get());
            session.getTransaction().commit();
            return Optional.of(doctorDto);
        } else {
            session.getTransaction().rollback();
            return Optional.empty();
        }
    }
}
