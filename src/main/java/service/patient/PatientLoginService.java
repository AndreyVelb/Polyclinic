package service.patient;

import entity.Patient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.PatientRepository;
import servlet.converter.request.PatientLoginConverter;
import service.mapper.PatientDtoMapper;
import service.dto.patient.PatientDto;
import service.dto.patient.PatientLoginDto;
import util.SessionPool;

import java.util.Optional;

@RequiredArgsConstructor
public class PatientLoginService {

    private final PatientRepository patientRepository;

    private final PatientLoginConverter patientLoginConverter;
    private final PatientDtoMapper patientDtoMapper;

    @SneakyThrows
    public Optional<PatientDto> authenticate(HttpServletRequest request){
        Session session = SessionPool.getSession();
        PatientLoginDto patientLoginDto = patientLoginConverter.convert(request);
        session.beginTransaction();
        Optional<Patient> mayBePatient = patientRepository.authenticate(patientLoginDto.getLogin(), patientLoginDto.getPassword(), session);
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
