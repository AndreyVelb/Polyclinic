package servlet.performer;

import jakarta.servlet.http.HttpServletRequest;
import servlet.performer.doctor.DoctorLoginPerformer;
import servlet.performer.doctor.PatientPagePerformer;
import servlet.performer.doctor.PatientSearcherPerformer;

import java.util.*;

public class PerformerDispatcher {
    private static List<Performer> allPerformers;

    private static final PatientRegistrationPerformer patientRegistrationPerformer = new PatientRegistrationPerformer();
    private static final PatientLoginPerformer patientLoginPerformer = new PatientLoginPerformer();
    private static final DoctorLoginPerformer doctorLoginPerformer = new DoctorLoginPerformer();
    private static final PatientSearcherPerformer patientSearcherPerformer = new PatientSearcherPerformer();
    private static final PatientPagePerformer patientPagePerformer = new PatientPagePerformer();

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
        performerList.add(patientSearcherPerformer);
        performerList.add(patientPagePerformer);

        return performerList;
    }
}
