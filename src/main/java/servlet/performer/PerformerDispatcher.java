package servlet.performer;

import jakarta.servlet.http.HttpServletRequest;
import servlet.performer.doctor.*;
import servlet.performer.patient.PatientLoginPerformer;
import servlet.performer.patient.PatientRegistrationPerformer;

import java.util.*;

public class PerformerDispatcher {
    private static List<Performer> allPerformers;

    private static final PatientRegistrationPerformer patientRegistrationPerformer = new PatientRegistrationPerformer();
    private static final PatientLoginPerformer patientLoginPerformer = new PatientLoginPerformer();
    private static final DoctorLoginPerformer doctorLoginPerformer = new DoctorLoginPerformer();
    private static final SearchPatientPerformer searchPatientPerformer = new SearchPatientPerformer();
    private static final MedicCardPerformer medicCardPerformer = new MedicCardPerformer();
    private static final AppointmentRecordCreatePerformer appointmentRecordCreatePerformer = new AppointmentRecordCreatePerformer();
    public static final AppointmentRecordPerformer appointmentRecordPerformer = new AppointmentRecordPerformer();
    private static final PatientsRecordsPerformer patientsRecordsPerformer = new PatientsRecordsPerformer();

    public PerformerDispatcher(){
        allPerformers = createPerformerList();
    }

    public Optional<Performer> definePerformer(HttpServletRequest request){
        for (Performer performer : allPerformers){
            if (performer.isAppropriatePath(request)){
                return Optional.of(performer);
            }
        }
        return Optional.empty();
    }

    private ArrayList<Performer> createPerformerList(){
        ArrayList<Performer> performerList = new ArrayList<>();
        performerList.add(patientRegistrationPerformer);
        performerList.add(doctorLoginPerformer);
        performerList.add(patientLoginPerformer);
        performerList.add(searchPatientPerformer);
        performerList.add(medicCardPerformer);
        performerList.add(appointmentRecordCreatePerformer);
        performerList.add(appointmentRecordPerformer);
        performerList.add(patientsRecordsPerformer);

        return performerList;
    }
}
