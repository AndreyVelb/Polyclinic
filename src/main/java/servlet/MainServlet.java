package servlet;

import exception.*;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.AppointmentRecordRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import service.doctor.AppointmentRecordCreateService;
import service.doctor.AppointmentRecordService;
import service.doctor.DoctorLoginService;
import servlet.response.ExceptionResponse;
import servlet.performer.Performer;
import servlet.performer.PerformerDispatcher;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.*;

@WebServlet("/*")
public class MainServlet extends HttpServlet {
    private AppointmentRecordRepository appointmentRecordRepository;
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;

    private AppointmentRecordCreateService appointmentRecordCreateService;
    private AppointmentRecordService appointmentRecordService;
    private DoctorLoginService doctorLoginService;

    private static PerformerDispatcher performerDispatcher;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        performerDispatcher = new PerformerDispatcher();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var writer = resp.getWriter();
            Optional<Performer> mayBePerformer = performerDispatcher.definePerformer(req);
            if (mayBePerformer.isPresent()) {
                Performer performer = mayBePerformer.get();
                if (performer.isMethodCanBePerformed(req)) {
                    performer.performAndSendResponse(writer, req, resp);
                } else {
                    throw new MethodNotAllowedException();
                }
            }else {
                throw new PageNotFoundException();
            }
        } catch (MethodNotAllowedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_METHOD_NOT_ALLOWED);
        }catch (PageNotFoundException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_NOT_FOUND);
        }catch (AlreadyExistsException
                | ConstraintViolationException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_BAD_REQUEST);
        } catch (ServerTechnicalProblemsException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_INTERNAL_SERVER_ERROR);
        }catch (NotAuthenticatedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_UNAUTHORIZED);
        }
    }

        @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var writer = resp.getWriter();
            Optional<Performer> mayBePerformer = performerDispatcher.definePerformer(req);
            if (mayBePerformer.isPresent()) {
                Performer performer = mayBePerformer.get();
                if (performer.isMethodCanBePerformed(req)) {
                    performer.performAndSendResponse(writer, req, resp);
                } else{
                    throw new MethodNotAllowedException();
                }
            } else {
                throw  new PageNotFoundException();
            }
        }catch (MethodNotAllowedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_METHOD_NOT_ALLOWED);
        }catch (PageNotFoundException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_NOT_FOUND);
        }catch (AlreadyExistsException
                | ConstraintViolationException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_BAD_REQUEST);
        } catch (ServerTechnicalProblemsException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_INTERNAL_SERVER_ERROR);
        }catch (NotAuthenticatedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_UNAUTHORIZED);
        }



//        try (PrintWriter writer = resp.getWriter()){
//            patientRegistrationService.registration(writer, req, resp);
//        } catch (IOException e) {
//            try {
//                new ExceptionResponse().send(resp.getWriter(), resp, new ServerTechnicalProblemsException(), SC_INTERNAL_SERVER_ERROR);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
    }


}
