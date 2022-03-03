package service.doctor;

import entity.newdb.PatientNewDB;
import exception.PageNotFoundException;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.newdb.PatientNewDBRepository;
import service.dto.patient.PatientDto;
import service.dto.validator.DtoValidator;
import service.mapper.to.PatientDtoMapper;
import servlet.converter.request.PatientLastNameConverter;
import util.NewDBSessionPool;

import java.util.Optional;

public class PatientPageService {
    private final Session session;

    private final PatientNewDBRepository patientRepository;

    private final PatientDtoMapper patientDtoMapper;

    public PatientPageService(){
        this.session = NewDBSessionPool.getSession();
        this.patientRepository = new PatientNewDBRepository(session);
        this.patientDtoMapper = new PatientDtoMapper();
    }

    @SneakyThrows
    public PatientDto getPatientPage(Long id){
        Optional<PatientNewDB> mayBePatient = patientRepository.findById(id);
        if (mayBePatient.isPresent()){
            return patientDtoMapper.mapFrom(mayBePatient.get());
        }else {
            throw new PageNotFoundException();
        }
    }
}
