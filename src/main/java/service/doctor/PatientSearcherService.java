package service.doctor;

import entity.newdb.PatientNewDB;
import exception.DtoValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import repository.newdb.PatientNewDBRepository;
import servlet.converter.request.PatientLastNameConverter;
import service.mapper.to.PatientDtoMapper;
import service.dto.validator.DtoValidator;
import service.dto.patient.PatientDto;
import service.dto.doctor.PatientLastNameDto;
import util.NewDBSessionPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientSearcherService {
    private final Session session;

    private final PatientNewDBRepository patientRepository;

    private final PatientLastNameConverter patientLastNameConverter;
    private final PatientDtoMapper patientDtoMapper;

    private final DtoValidator dtoValidator;

    public PatientSearcherService(){
        this.session = NewDBSessionPool.getSession();
        this.patientRepository = new PatientNewDBRepository(session);
        this.patientLastNameConverter = new PatientLastNameConverter();
        this.dtoValidator = new DtoValidator();
        this.patientDtoMapper = new PatientDtoMapper();
    }

    public List<PatientDto> findPatientsByLastName(HttpServletRequest request) throws IOException, DtoValidationException {
        PatientLastNameDto patientLastNameDto = patientLastNameConverter.convert(request);

        ArrayList<PatientNewDB> patientsList = new ArrayList<>();
        if (dtoValidator.isValid(patientLastNameDto)){
            session.beginTransaction();
            patientsList = patientRepository.findByLastName(patientLastNameDto.getLastName());
            session.getTransaction().commit();
        }
        ArrayList<PatientDto> dtoPatientsList = new ArrayList<>();
        patientsList.forEach(patient -> dtoPatientsList.add(patientDtoMapper.mapFrom(patient))); //?????????
        return dtoPatientsList;
    }
}
