package servlet.performer;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import servlet.performer.admin.*;
import servlet.performer.doctor.*;
import servlet.performer.patient.*;

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
    private final DoctorChoicePerformer doctorChoicePerformer;
    private final DoctorInfoPerformer doctorInfoPerformer;
    private final DoctorsAppointmentsChoicePerformer doctorsAppointmentsPerformer;
    private final BookingDoctorsAppointmentPerformer bookingDoctorsAppointmentPerformer;
    private final PatientLogoutPerformer patientLogoutPerformer;
    private final AdminHomePagePerformer adminHomePagePerformer;
    private final DoctorRegistrationPerformer doctorRegistrationPerformer;
    private final NextWeekTimetablePerformer nextWeekTimetablePerformer;
    private final TimetablePerformer timetablePerformer;
    private final AdminLogoutPerformer adminLogoutPerformer;

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
