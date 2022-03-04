package service.doctor;

import entity.Patient;
import exception.PageNotFoundException;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.PatientRepository;
import service.dto.patient.PatientDto;
import service.mapper.to.PatientDtoMapper;
import service.mapper.util.SessionPool;

import java.util.Optional;

public class PatientPageService {
    private final Session session;

    private final PatientRepository patientRepository;

    private final PatientDtoMapper patientDtoMapper;

    public PatientPageService(){
        this.session = SessionPool.getSession();
        this.patientRepository = new PatientRepository(session);
        this.patientDtoMapper = new PatientDtoMapper();
    }

    @SneakyThrows
    public PatientDto getPatientPage(Long id){
        Optional<Patient> mayBePatient = patientRepository.findById(id);
        if (mayBePatient.isPresent()){
            return patientDtoMapper.mapFrom(mayBePatient.get());
        }else {
            throw new PageNotFoundException();
        }
    }
}
