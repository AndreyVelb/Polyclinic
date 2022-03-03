package service.patient;

import entity.newdb.PatientNewDB;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.newdb.PatientNewDBRepository;
import servlet.converter.request.PatientLoginConverter;
import service.mapper.to.PatientDtoMapper;
import service.dto.patient.PatientDto;
import service.dto.patient.PatientLoginDto;
import util.NewDBSessionPool;

import java.util.Optional;

public class PatientLoginService {
    private final Session session;

    private final PatientNewDBRepository patientRepository;

    private final PatientLoginConverter patientLoginConverter;
    private final PatientDtoMapper patientDtoMapper;


    public PatientLoginService(){
        this.session = NewDBSessionPool.getSession();
        this.patientRepository = new PatientNewDBRepository(session);
        this.patientLoginConverter = new PatientLoginConverter();
        this.patientDtoMapper = new PatientDtoMapper();
    }

    @SneakyThrows
    public Optional<PatientDto> authenticate(HttpServletRequest request){
        PatientLoginDto patientLoginDto = patientLoginConverter.convert(request);
        session.beginTransaction();
        Optional<PatientNewDB> mayBePatient = patientRepository.authenticate(patientLoginDto.getLogin(), patientLoginDto.getPassword());
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
