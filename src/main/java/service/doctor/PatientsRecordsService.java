package service.doctor;

import entity.AppointmentRecord;
import entity.Patient;
import exception.PageNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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

public class PatientsRecordsService {
    private final Session session;

    private final PatientRepository patientRepository;
    private final AppointmentRecordRepository appointmentRecordRepository;
    private final DoctorRepository doctorRepository;

    private final AppointmentRecordRequestConverter appointmentRecordConverter;
    private final DoctorDtoMapper doctorDtoMapper;
    private final AppointmentRecordMapper appointmentRecordMapper;
    private final AppointmentRecordDtoMapper appointmentRecordDtoMapper;

    public PatientsRecordsService(){
        this.session = SessionPool.getSession();
        this.patientRepository = new PatientRepository(session);
        this.doctorRepository = new DoctorRepository(session);
        this.appointmentRecordRepository = new AppointmentRecordRepository(session);
        this.appointmentRecordConverter = new AppointmentRecordRequestConverter();
        this.doctorDtoMapper = new DoctorDtoMapper();
        this.appointmentRecordMapper = new AppointmentRecordMapper();
        this.appointmentRecordDtoMapper = new AppointmentRecordDtoMapper();
    }

    @SneakyThrows
    public List<AppointmentRecordDto> getPatientsRecordsDto(HttpServletRequest request){
        Long id = extractPatientIdFromRequest(request);
        try {
            session.beginTransaction();
            Optional<Patient> patient = patientRepository.findById(id);
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

//    private List<AppointmentRecordDto> mapToAppRecordDtoList(List<AppointmentRecord> patientsRecords){
//
//        return dtoRecordsList;
//    }

    private Long extractPatientIdFromRequest(HttpServletRequest request){
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[3]);       // 0-""/ 1-"doctor"/ 2-"patients"/ 3-"{some id}"/...
    }
}
