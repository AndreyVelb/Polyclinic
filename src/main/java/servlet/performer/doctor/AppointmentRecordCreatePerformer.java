package servlet.performer.doctor;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.doctor.AppointmentRecordCreateService;
import service.dto.doctor.AppointmentRecordDto;
import servlet.performer.Performer;
import servlet.response.AppointmentRecordCreateResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;

/**
 *      /doctor/{id}/patients/{id}/writing-record
 */

@RequiredArgsConstructor
public class AppointmentRecordCreatePerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_PATH;
    private static final String patientsSubPath = UrlPath.DOCTOR_SUBPATH_PATIENTS;
    private static final String writingRecordSubPath = UrlPath.DOCTOR_SUBPATH_WRITING_RECORD;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final AppointmentRecordCreateService service;


    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, AlreadyExistsException, ServerTechnicalProblemsException, NotAuthenticatedException, PageNotFoundException {
        if (request.getMethod().equals(HttpMethod.POST)){
            performPOST(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performPOST(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        AppointmentRecordDto appointmentRecordDto = service.writeAndSaveAppointmentRecord(request);
        new AppointmentRecordCreateResponse().send(writer, response, appointmentRecordDto, SC_CREATED);
    }

    @Override
    public boolean isMethodCanBePerformed(HttpServletRequest request) {
        for (String method : performableMethods) {
            if (method.equals(request.getMethod())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAppropriatePath(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        if(requestPath.startsWith(path)){
            String[] requestPathParts = request.getPathInfo().split("/");
            if(requestPathParts.length == 6
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(patientsSubPath)
                    && requestPathParts[4].matches("[1-90]+")
                    && requestPathParts[5].matches(writingRecordSubPath)){  // 0-""/ 1-"doctor"/ 2-"{some id}"/ 3-"patients"/ 4-"{some id}"/ 5-"writing-record"
                return true;
            }else return false;
        }else return false;
    }
}
