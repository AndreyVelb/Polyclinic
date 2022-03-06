package servlet.performer.doctor;

import entity.AppointmentRecord;
import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.doctor.AppointmentRecordCreateService;
import service.doctor.AppointmentRecordService;
import service.dto.doctor.AppointmentRecordDto;
import servlet.converter.response.AppointmentRecordConverter;
import servlet.performer.Performer;
import servlet.response.AppointmentRecordCreateResponse;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;

public class AppointmentRecordPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_PATH_WITHOUT_INFO;
    private static final String subPath = "records";
    private static final ArrayList<String> performableMethods = new ArrayList<>();

    private final AppointmentRecordService service = new AppointmentRecordService();
    private final AppointmentRecordConverter appointmentRecordConverter = new AppointmentRecordConverter();

    public AppointmentRecordPerformer(){
        performableMethods.add(HttpMethod.GET);
    }

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, AlreadyExistsException, ServerTechnicalProblemsException, NotAuthenticatedException, PageNotFoundException {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        AppointmentRecordDto appointmentRecordDto = service.getAppointmentRecordDto(request);
        String json = appointmentRecordConverter.convert(appointmentRecordDto);
        new JsonResponse().send(writer, response, json, SC_OK);
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
                    && requestPathParts[4].matches(subPath)
                    && requestPathParts[5].matches("[1-90]+")){  // 0-""/ 1-"doctor"/ 2-"patients"/ 3-"{some id}"/ 4-"records/ 5-"{some record-id}"
                return true;
            }else return false;
        }else return false;
    }
}
