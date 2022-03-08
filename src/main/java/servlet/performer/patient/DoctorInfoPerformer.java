package servlet.performer.patient;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.dto.doctor.DoctorDto;
import service.patient.DoctorInfoService;
import servlet.converter.response.DoctorDtoConverter;
import servlet.performer.Performer;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 *      /patient/{id}/doctors/{id}
 */

@RequiredArgsConstructor
public class DoctorInfoPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_PATH;
    private static final String doctorsSubPath = UrlPath.PATIENT_SUBPATH_DOCTORS;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final DoctorInfoService service;

    private final DoctorDtoConverter doctorDtoConverter;


    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, AlreadyExistsException, ServerTechnicalProblemsException, NotAuthenticatedException, PageNotFoundException {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        DoctorDto doctorDto = service.getDoctorDto(request);
        String doctorDtoAsJson = doctorDtoConverter.convert(doctorDto);
//        new JsonResponse().send(writer, response, json, SC_OK);
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
            return requestPathParts.length == 5
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(doctorsSubPath)
                    && requestPathParts[4].matches("[1-90]+");  // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-{id}
        }else return false;
    }
}
