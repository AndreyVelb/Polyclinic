package service.patient;

import entity.Doctor;
import exception.PageNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorRepository;
import service.Mapper;
import service.dto.doctor.DoctorDto;
import util.SessionPool;

@RequiredArgsConstructor
public class DoctorInfoService {

    private final DoctorRepository doctorRepository;

    private final Mapper mapper;

    @SneakyThrows
    public DoctorDto getDoctorDto(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        Long doctorId = extractDoctorIdFromRequest(request);
        session.beginTransaction();
        try {
            Doctor doctor = doctorRepository.findById(doctorId, session).orElseThrow(PageNotFoundException::new);
            session.getTransaction().commit();
            return mapper.mapToDoctorDto(doctor);
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }

    private Long extractDoctorIdFromRequest(HttpServletRequest request) {
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[4]);       // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-"{id}"/...
    }
}
