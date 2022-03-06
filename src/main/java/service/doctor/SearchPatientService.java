package service.doctor;

import entity.Patient;
import exception.DtoValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.PatientRepository;
import servlet.converter.request.PatientLastNameConverter;
import service.mapper.PatientDtoMapper;
import service.dto.validator.DtoValidator;
import service.dto.patient.PatientDto;
import service.dto.doctor.PatientLastNameDto;
import util.SessionPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SearchPatientService {

    private final PatientRepository patientRepository;

    private final PatientLastNameConverter patientLastNameConverter;
    private final PatientDtoMapper patientDtoMapper;

    private final DtoValidator dtoValidator;

    @SneakyThrows
    public List<PatientDto> findPatientsByLastName(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        PatientLastNameDto patientLastNameDto = PatientLastNameDto.builder()
                .lastName(request.getParameter("lastName"))
                .build();                                        //patientLastNameConverter.convert(request);
        ArrayList<Patient> patientsList = new ArrayList<>();
        if (dtoValidator.isValid(patientLastNameDto)){
            session.beginTransaction();
            patientsList = patientRepository.findByLastName(patientLastNameDto.getLastName(), session);
            session.getTransaction().commit();
        }
        ArrayList<PatientDto> dtoPatientsList = new ArrayList<>();
        patientsList.forEach(patient -> dtoPatientsList.add(patientDtoMapper.mapFrom(patient)));
        return dtoPatientsList;
    }
}
