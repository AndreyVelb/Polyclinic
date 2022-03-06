package servlet.performer.doctor;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.doctor.AppointmentRecordCreateService;
import service.dto.doctor.AppointmentRecordDto;
import servlet.performer.Performer;
import servlet.response.AppointmentRecordCreateResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;

public class AppointmentRecordCreatePerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_PATH_WITHOUT_INFO;
    private static final String subPath = "writing-record";
    private static final ArrayList<String> performableMethods = new ArrayList<>();

    private final AppointmentRecordCreateService service = new AppointmentRecordCreateService();

    public AppointmentRecordCreatePerformer(){
        performableMethods.add(HttpMethod.POST);
    }

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
            if(requestPathParts.length == 5 && requestPathParts[4].matches(subPath)){  // 0-""/ 1-"doctor"/ 2-"patients"/ 3-"{some id}"/ 4-"writing-record
                return true;
            }else return false;
        }else return false;
    }
}
