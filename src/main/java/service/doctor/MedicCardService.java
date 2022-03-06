package service.doctor;

import entity.Patient;
import exception.PageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.PatientRepository;
import service.dto.patient.PatientDto;
import service.mapper.PatientDtoMapper;
import util.SessionPool;

import java.util.Optional;

@RequiredArgsConstructor
public class MedicCardService {

    private final PatientRepository patientRepository;

    private final PatientDtoMapper patientDtoMapper;

    @SneakyThrows
    public PatientDto getPatientMedicCard(Long id){
        Session session = SessionPool.getSession();
        session.beginTransaction();
        Optional<Patient> mayBePatient = patientRepository.findById(id, session);
        if (mayBePatient.isPresent()){
            session.getTransaction().commit();
            return patientDtoMapper.mapFrom(mayBePatient.get());
        }else {
            session.getTransaction().rollback();
            throw new PageNotFoundException();
        }
    }
}
