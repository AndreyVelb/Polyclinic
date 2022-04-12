package service.doctor;

import entity.Patient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.PatientRepository;
import service.Mapper;
import service.dto.doctor.PatientLastNameDto;
import service.dto.patient.PatientDto;
import service.dto.validator.DtoValidator;
import util.SessionPool;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SearchPatientService {

    private final PatientRepository patientRepository;

    private final Mapper mapper;

    private final DtoValidator dtoValidator;

    @SneakyThrows
    public List<PatientDto> findPatientsByLastName(HttpServletRequest request) {
        Session session = SessionPool.getSession();
        PatientLastNameDto patientLastNameDto = PatientLastNameDto.builder()
                .lastName(request.getParameter("lastName"))
                .build();
        ArrayList<Patient> patientsList;
        try {
            dtoValidator.validate(patientLastNameDto);
            session.beginTransaction();
            patientsList = patientRepository.findByLastName(patientLastNameDto.getLastName(), session);
            session.getTransaction().commit();
            ArrayList<PatientDto> patientDtoList = new ArrayList<>();
            patientsList.forEach(patient -> patientDtoList.add(mapper.mapToPatientDto(patient)));
            return patientDtoList;
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }
}
