package service.doctor;

import entity.Patient;
import exception.PageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.PatientRepository;
import service.Mapper;
import service.dto.patient.PatientDto;
import util.SessionPool;

@RequiredArgsConstructor
public class MedicCardService {

    private final PatientRepository patientRepository;

    private final Mapper mapper;

    @SneakyThrows
    public PatientDto getPatientMedicCard(Long id){
        Session session = SessionPool.getSession();
        session.beginTransaction();
        try {
            Patient patient = patientRepository.findById(id, session).orElseThrow(PageNotFoundException::new);
            session.getTransaction().commit();
            return mapper.mapToPatientDto(patient);
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw new PageNotFoundException();
        }
    }
}
