package servlet.performer.doctor;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.doctor.PatientsRecordsService;
import service.dto.doctor.AppointmentRecordDto;
import servlet.converter.response.AppointmentRecordDtoListConverter;
import servlet.performer.Performer;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

public class PatientsRecordsPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_PATH_WITHOUT_INFO;
    private static final String subPath = "records";
    private static final ArrayList<String> performableMethods = new ArrayList<>();

    private final PatientsRecordsService service = new PatientsRecordsService();

    private final AppointmentRecordDtoListConverter appointmentRecordConverter = new AppointmentRecordDtoListConverter();

    public PatientsRecordsPerformer(){
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
        List<AppointmentRecordDto> patientsRecordsDtoList = service.getPatientsRecordsDto(request);
        String json = appointmentRecordConverter.convert(patientsRecordsDtoList);
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
            if(requestPathParts.length == 5 && requestPathParts[4].matches(subPath)){  // 0-""/ 1-"doctor"/ 2-"patients"/ 3-"{some id}"/ 4-"records"
                return true;
            }else return false;
        }else return false;
    }
}
