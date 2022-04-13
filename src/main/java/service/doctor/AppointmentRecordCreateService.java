package service.doctor;

import entity.AppointmentRecord;
import entity.Doctor;
import entity.Patient;
import exception.DoctorNotFoundException;
import exception.PatientNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import repository.AppointmentRecordRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import service.dto.doctor.AppointmentRecordRequestDto;
import service.dto.doctor.DoctorDto;
import service.dto.validator.DtoValidator;
import util.ExceptionMessage;
import util.SessionPool;
import util.SessionRole;

import java.time.LocalDate;

@RequiredArgsConstructor
public class AppointmentRecordCreateService {

    private final PatientRepository patientRepository;
    private final AppointmentRecordRepository appointmentRecordRepository;
    private final DoctorRepository doctorRepository;

    private final DtoValidator dtoValidator;

    public Long writeAndSaveAppointmentRecord(HttpServletRequest request, AppointmentRecordRequestDto appointmentRecordRequestDto) {
        dtoValidator.validate(appointmentRecordRequestDto);
        Session session = SessionPool.getSession();
        try {
            DoctorDto doctorDto = (DoctorDto) request.getSession().getAttribute(SessionRole.getDOCTOR());
            session.beginTransaction();
            Doctor doctor = doctorRepository.findById(doctorDto.getId(), SessionPool.getSession())
                    .orElseThrow(() -> new DoctorNotFoundException(ExceptionMessage.DOCTOR_NOT_FOUND));
            Long patientId = appointmentRecordRequestDto.getPatientId();
            Patient patient = patientRepository.findById(patientId, session)
                    .orElseThrow(() -> new PatientNotFoundException(ExceptionMessage.PATIENT_NOT_FOUND));
            AppointmentRecord appointmentRecordWithoutId = AppointmentRecord.builder()
                    .doctor(doctor)
                    .patient(patient)
                    .visitDate(LocalDate.now())
                    .healthComplaints(appointmentRecordRequestDto.getHealthComplaints())
                    .doctorsRecommendation(appointmentRecordRequestDto.getDoctorsRecommendation())
                    .build();
            Long appRecordId = appointmentRecordRepository.save(appointmentRecordWithoutId, session);
            session.getTransaction().commit();
            return appRecordId;
        } catch (Exception exception) {
            session.getTransaction().rollback();
            throw exception;
        }
    }
}
