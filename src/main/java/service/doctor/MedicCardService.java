package service.doctor;

import entity.Patient;
import exception.PageNotFoundException;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.PatientRepository;
import service.dto.patient.PatientDto;
import service.mapper.PatientDtoMapper;
import util.SessionPool;

import java.util.Optional;

public class MedicCardService {
    private final Session session;

    private final PatientRepository patientRepository;

    private final PatientDtoMapper patientDtoMapper;

    public MedicCardService(){
        this.session = SessionPool.getSession();
        this.patientRepository = new PatientRepository(session);
        this.patientDtoMapper = new PatientDtoMapper();
    }

    @SneakyThrows
    public PatientDto getPatientMedicCard(Long id){
        session.beginTransaction();
        Optional<Patient> mayBePatient = patientRepository.findById(id);
        if (mayBePatient.isPresent()){
            session.getTransaction().commit();
            return patientDtoMapper.mapFrom(mayBePatient.get());
        }else {
            session.getTransaction().rollback();
            throw new PageNotFoundException();
        }
    }
}
