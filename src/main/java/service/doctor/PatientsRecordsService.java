package service.doctor;

import entity.Patient;
import exception.PageNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.PatientRepository;
import service.Mapper;
import service.dto.doctor.AppointmentRecordDto;
import util.SessionPool;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PatientsRecordsService {

    private final PatientRepository patientRepository;

    private final Mapper mapper;

    @SneakyThrows
    public List<AppointmentRecordDto> getPatientsRecordsDto(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        Long id = extractPatientIdFromRequest(request);
        session.beginTransaction();
        try {
            Patient patient = patientRepository.findById(id, session)
                    .orElseThrow(PageNotFoundException::new);
            return patient.getPatientsRecords().stream()
                    .map(mapper::mapToAppRecordDto)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }

    private Long extractPatientIdFromRequest(HttpServletRequest request) {
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[4]);       // 0-""/ 1-"doctor"/ 2-{id}/ 3-"patients"/ 4-"{some id}"/...
    }
}
