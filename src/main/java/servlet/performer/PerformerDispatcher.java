package servlet.performer;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    private final List<Performer> allPerformers;

    public Optional<Performer> definePerformer(HttpServletRequest request){
//        List<Performer> allPerformers = createPerformerList();
        for (Performer performer : allPerformers){
            if (performer.isAppropriatePath(request)){
                return Optional.of(performer);
            }
        }
        return Optional.empty();
    }

    private ArrayList<Performer> createPerformerList(){
        ArrayList<Performer> performerList = new ArrayList<>();
        performerList.add(doctorLoginPerformer);
        performerList.add(searchPatientPerformer);
        performerList.add(medicCardPerformer);
        performerList.add(appointmentRecordCreatePerformer);
        performerList.add(appointmentRecordPerformer);
        performerList.add(patientsRecordsPerformer);
        performerList.add(doctorLogoutPerformer);
        performerList.add(patientRegistrationPerformer);
        performerList.add(patientLoginPerformer);
        performerList.add(patientLogoutPerformer);

        return performerList;
    }
}
