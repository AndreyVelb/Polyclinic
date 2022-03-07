package servlet.performer.patient;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.dto.doctor.DoctorDto;
import service.patient.DoctorChoiceService;
import servlet.converter.response.DoctorDtoListConverter;
import servlet.performer.Performer;
import servlet.response.AppointmentRecordCreateResponse;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@RequiredArgsConstructor
public class DoctorChoicePerformer implements Performer {
    private static final String path = UrlPath.PATIENT_CHOOSE_DOCTOR;
    private static final Set<String> performableMethods = Set.of(HttpMethod.GET);

    private final DoctorChoiceService service;

    private final DoctorDtoListConverter doctorDtoListConverter;


    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, AlreadyExistsException, ServerTechnicalProblemsException, NotAuthenticatedException, PageNotFoundException {
        if (request.getMethod().equals(HttpMethod.GET)){
            performGET(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        ArrayList<DoctorDto> doctorDtoList = service.getAllDoctors();
        String json = doctorDtoListConverter.convert(doctorDtoList);
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
            if (request.getPathInfo().split("/").length == 3){      // 0-""/ 1-"patient"/ 2-"doctors"
                return true;
            }else return false;
        }else return false;
    }
}
