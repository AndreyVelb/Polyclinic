package servlet.performer.patient;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.dto.patient.DocAppForPatientDto;
import service.patient.BookingDoctorsAppointmentService;
import servlet.converter.response.DocAppDtoForPatientConverter;
import servlet.converter.response.InfoConverter;
import servlet.performer.Performer;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import javax.persistence.OptimisticLockException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;

/**
 *      /patient/{id}/doctors/{id}/appointments/{id}
 */

@RequiredArgsConstructor
public class BookingDoctorsAppointmentPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_PATH;
    private static final String doctorsSubPath = UrlPath.PATIENT_SUBPATH_DOCTORS;
    private static final String appointmentsSubPath = UrlPath.PATIENT_SUBPATH_DOCTORS_APPOINTMENTS;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET, HttpMethod.PUT);

    private final BookingDoctorsAppointmentService service;

    private final DocAppDtoForPatientConverter docAppDtoConverter;
    private final InfoConverter infoConverter;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, UserAlreadyExistsException, ServerTechnicalProblemsException, NotAuthenticatedException, PageNotFoundException {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }
        if (request.getMethod().equals(HttpMethod.PUT)){
            performPUT(writer, request, response);
        }
        else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        DocAppForPatientDto dto = service.getDoctorsAppointment(request);
        String docAppDtoAsJson = docAppDtoConverter.convert(dto);
        new JsonResponse().send(writer, response, docAppDtoAsJson, SC_OK);
    }

    @SneakyThrows
    private void performPUT(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        try {
            service.bookDoctorsAppointment(request);
        }catch (OptimisticLockException exception){
            throw new AlreadyBookedException();
        }
        String json = infoConverter.convert("Вы успешно забронировали это время приема");
        new JsonResponse().send(writer, response, json, SC_CREATED);
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
            return requestPathParts.length == 7
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(doctorsSubPath)
                    && requestPathParts[4].matches("[1-90]+")
                    && requestPathParts[5].matches(appointmentsSubPath)
                    && requestPathParts[6].matches("[1-90]+");      // 0-""/ 1-"patient"/ 2-{id}/ 3-"doctors"/ 4-{id}/ 5-"appointments"/ 6-{id}
        }else return false;
    }
}
