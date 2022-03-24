package service.patient;

import entity.Doctor;
import exception.PageNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorRepository;
import service.dto.doctor.DoctorDto;
import service.mapper.DoctorDtoMapper;
import util.SessionPool;

import java.util.Optional;

@RequiredArgsConstructor
public class DoctorInfoService {

    private final DoctorRepository doctorRepository;

    private final DoctorDtoMapper doctorDtoMapper;

    @SneakyThrows
    public DoctorDto getDoctorDto(HttpServletRequest request){
        Session session = SessionPool.getSession();
        Long doctorId = extractDoctorIdFromRequest(request);
        try {
            session.beginTransaction();
            Optional<Doctor> mayBeDoctor = doctorRepository.findById(doctorId, session);
            session.getTransaction().commit();
            if (mayBeDoctor.isPresent()){
                return doctorDtoMapper.mapFrom(mayBeDoctor.get());
            }else throw new PageNotFoundException();
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
    }

    private Long extractDoctorIdFromRequest(HttpServletRequest request){
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[4]);       // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-"{id}"/...
    }
}
