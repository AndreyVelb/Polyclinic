package service.patient;

import entity.Patient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.PatientRepository;
import servlet.converter.request.PatientLoginConverter;
import service.mapper.to.PatientDtoMapper;
import service.dto.patient.PatientDto;
import service.dto.patient.PatientLoginDto;
import service.mapper.util.SessionPool;

import java.util.Optional;

public class PatientLoginService {
    private final Session session;

    private final PatientRepository patientRepository;

    private final PatientLoginConverter patientLoginConverter;
    private final PatientDtoMapper patientDtoMapper;


    public PatientLoginService(){
        this.session = SessionPool.getSession();
        this.patientRepository = new PatientRepository(session);
        this.patientLoginConverter = new PatientLoginConverter();
        this.patientDtoMapper = new PatientDtoMapper();
    }

    @SneakyThrows
    public Optional<PatientDto> authenticate(HttpServletRequest request){
        PatientLoginDto patientLoginDto = patientLoginConverter.convert(request);
        session.beginTransaction();
        Optional<Patient> mayBePatient = patientRepository.authenticate(patientLoginDto.getLogin(), patientLoginDto.getPassword());
        if (mayBePatient.isPresent()){
            PatientDto patientDto = patientDtoMapper.mapFrom(mayBePatient.get());
            session.getTransaction().commit();
            return Optional.of(patientDto);
        } else {
            session.getTransaction().rollback();
            return Optional.empty();
        }
    }
}
