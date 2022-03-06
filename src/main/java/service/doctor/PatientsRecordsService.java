package service.doctor;

import entity.AppointmentRecord;
import entity.Patient;
import exception.PageNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.AppointmentRecordRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import service.dto.doctor.AppointmentRecordDto;
import service.mapper.AppointmentRecordDtoMapper;
import service.mapper.AppointmentRecordMapper;
import service.mapper.DoctorDtoMapper;
import servlet.converter.request.AppointmentRecordRequestConverter;
import util.SessionPool;

import java.util.*;

@RequiredArgsConstructor
public class PatientsRecordsService {

    private final PatientRepository patientRepository;

    private final AppointmentRecordDtoMapper appointmentRecordDtoMapper;

    @SneakyThrows
    public List<AppointmentRecordDto> getPatientsRecordsDto(HttpServletRequest request){
        Session session = SessionPool.getSession();
        Long id = extractPatientIdFromRequest(request);
        try {
            session.beginTransaction();
            Optional<Patient> patient = patientRepository.findById(id, session);
            if (patient.isPresent()){
                List<AppointmentRecord> patientsRecords = patient.get().getPatientsRecords();
                List<AppointmentRecordDto> dtoRecordsList = new ArrayList<>();
                patientsRecords.stream().forEach(record -> dtoRecordsList.add(appointmentRecordDtoMapper.mapFrom(record)));
                session.getTransaction().commit();
                return dtoRecordsList;
            }else new PageNotFoundException();
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
        return List.of();
    }

    private Long extractPatientIdFromRequest(HttpServletRequest request){
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[3]);       // 0-""/ 1-"doctor"/ 2-"patients"/ 3-"{some id}"/...
    }
}
