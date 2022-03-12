package servlet.performer;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import servlet.performer.admin.DoctorRegistrationPerformer;
import servlet.performer.admin.NextWeekTimetablePerformer;
import servlet.performer.admin.TimetablePerformer;
import servlet.performer.doctor.*;
import servlet.performer.patient.PatientLoginPerformer;
import servlet.performer.patient.PatientLogoutPerformer;
import servlet.performer.patient.PatientRegistrationPerformer;

import java.util.*;

@RequiredArgsConstructor
public class PerformerDispatcher {
    private final DoctorLoginPerformer doctorLoginPerformer;
    private final SearchPatientPerformer searchPatientPerformer;
    private final MedicCardPerformer medicCardPerformer;
    private final AppointmentRecordCreatePerformer appointmentRecordCreatePerformer;
    private final AppointmentRecordPerformer appointmentRecordPerformer;
    private final PatientsRecordsPerformer patientsRecordsPerformer;
    private final DoctorLogoutPerformer doctorLogoutPerformer;
    private final PatientRegistrationPerformer patientRegistrationPerformer;
    private final PatientLoginPerformer patientLoginPerformer;
    private final PatientLogoutPerformer patientLogoutPerformer;
    private final DoctorRegistrationPerformer doctorRegistrationPerformer;
    private final NextWeekTimetablePerformer nextWeekTimetablePerformer;
    private final TimetablePerformer timetablePerformer;

    private final List<Performer> allPerformers;

    public Optional<Performer> definePerformer(HttpServletRequest request){
        for (Performer performer : allPerformers){
            if (performer.isAppropriatePath(request)){
                return Optional.of(performer);
            }
        }
        return Optional.empty();
    }
}
