package servlet.performer.patient;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.dto.patient.DocAppForPatientDto;
import service.patient.DoctorsAppointmentsService;
import servlet.converter.response.DocAppDtoListForPatientConverter;
import servlet.performer.Performer;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

/**
 *      /patient/{id}/doctors/{id}/appointments
 */

@RequiredArgsConstructor
public class DoctorsAppointmentsChoicePerformer implements Performer {
    private static final String path = UrlPath.PATIENT_PATH;
    private static final String doctorsSubPath = UrlPath.PATIENT_SUBPATH_DOCTORS;
    private static final String appointmentsSubPath = UrlPath.PATIENT_SUBPATH_DOCTORS_APPOINTMENTS;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final DoctorsAppointmentsService service;

    private final DocAppDtoListForPatientConverter docAppDtoListConverter;


    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, UserAlreadyExistsException, ServerTechnicalProblemsException, NotAuthenticatedException, PageNotFoundException {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        List<DocAppForPatientDto> docAppDtoList = service.getVacantDoctorsAppointments(request);
        String docAppDtoListAsJson = docAppDtoListConverter.convert(docAppDtoList);
        new JsonResponse().send(writer, response, docAppDtoListAsJson, SC_OK);
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
            return requestPathParts.length == 6
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(doctorsSubPath)
                    && requestPathParts[4].matches("[1-90]+")
                    && requestPathParts[5].matches(appointmentsSubPath);        // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-{id}/ 5-"appointments"
        }else return false;
    }
}
