package servlet;

import exception.*;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import repository.*;
import service.admin.AdminHomePageService;
import service.admin.DoctorRegistrationService;
import service.admin.NextWeekTimetableService;
import service.admin.TimetableService;
import service.doctor.*;
import service.dto.validator.DtoValidator;
import service.mapper.*;
import service.patient.*;
import servlet.converter.request.*;
import servlet.converter.response.*;
import servlet.performer.admin.*;
import servlet.performer.doctor.*;
import servlet.performer.patient.*;
import servlet.response.ExceptionResponse;
import servlet.performer.Performer;
import servlet.performer.PerformerDispatcher;

import javax.persistence.OptimisticLockException;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.*;

@WebServlet("/*")
public class MainServlet extends HttpServlet {

    private PerformerDispatcher performerDispatcher;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AppointmentRecordRepository appointmentRecordRepository = new AppointmentRecordRepository();
        DoctorRepository doctorRepository = new DoctorRepository();
        PatientRepository patientRepository = new PatientRepository();
        DoctorsAppointmentRepository doctorsAppointmentRepository = new DoctorsAppointmentRepository();
        WorkScheduleRepository workScheduleRepository = new WorkScheduleRepository();

        DtoValidator dtoValidator = new DtoValidator();

        ObjectMapper objectMapper = new ObjectMapper();
        AppointmentRecordRequestConverter appointmentRecordRequestConverter = new AppointmentRecordRequestConverter(objectMapper);
        DoctorLoginConverter doctorLoginConverter = new DoctorLoginConverter(objectMapper);
        PatientLastNameConverter patientLastNameConverter = new PatientLastNameConverter(objectMapper);
        PatientLoginConverter patientLoginConverter = new PatientLoginConverter(objectMapper);
        RegistrationPatientConverter registrationPatientConverter = new RegistrationPatientConverter(objectMapper);
        AppointmentRecordConverter appointmentRecordConverter = new AppointmentRecordConverter(objectMapper);
        AppointmentRecordDtoListConverter appointmentRecordDtoListConverter = new AppointmentRecordDtoListConverter(objectMapper);
        PatientDtoConverter patientDtoConverter = new PatientDtoConverter(objectMapper);
        PatientDtoListConverter patientDtoListConverter = new PatientDtoListConverter(objectMapper);
        DoctorDtoListConverter doctorDtoListConverter = new DoctorDtoListConverter(objectMapper);
        DocAppDtoListForAdminConverter docAppDtoListForAdminConverter = new DocAppDtoListForAdminConverter(objectMapper);
        DocAppDtoListForPatientConverter docAppDtoListForPatientConverter = new DocAppDtoListForPatientConverter(objectMapper);
        RegistrationDoctorConverter registrationDoctorConverter = new RegistrationDoctorConverter(objectMapper);
        AdminStatisticsDtoConverter adminStatisticsDtoConverter = new AdminStatisticsDtoConverter(objectMapper);
        DoctorDtoConverter doctorDtoConverter = new DoctorDtoConverter(objectMapper);
        DocAppDtoForPatientConverter docAppDtoForPatientConverter = new DocAppDtoForPatientConverter(objectMapper);
        InfoConverter infoConverter = new InfoConverter(objectMapper);

        DoctorDtoMapper doctorDtoMapper = new DoctorDtoMapper();
        PatientDtoMapper patientDtoMapper = new PatientDtoMapper();
        PatientMapper patientMapper = new PatientMapper();
        AppointmentRecordDtoMapper appointmentRecordDtoMapper = new AppointmentRecordDtoMapper(doctorDtoMapper, patientDtoMapper);
        AppointmentRecordMapper appointmentRecordMapper = new AppointmentRecordMapper();
        ScheduleAsMapDtoMapper scheduleAsListDtoMapper = new ScheduleAsMapDtoMapper();
        DocAppDtoForAdminMapper docAppDtoForAdminMapper = new DocAppDtoForAdminMapper(doctorDtoMapper, patientDtoMapper);
        DocAppDtoForPatientMapper docAppDtoForPatientMapper = new DocAppDtoForPatientMapper(doctorDtoMapper);
        DoctorMapper doctorMapper = new DoctorMapper();

        AppointmentRecordCreateService appointmentRecordCreateService = new AppointmentRecordCreateService(patientRepository, appointmentRecordRepository, doctorRepository,
                appointmentRecordRequestConverter, appointmentRecordMapper, appointmentRecordDtoMapper, dtoValidator);
        AppointmentRecordService appointmentRecordService = new AppointmentRecordService(appointmentRecordRepository, appointmentRecordDtoMapper);
        DoctorLoginService doctorLoginService = new DoctorLoginService(doctorRepository, doctorLoginConverter, doctorDtoMapper);
        MedicCardService medicCardService = new MedicCardService(patientRepository, patientDtoMapper);
        PatientsRecordsService patientsRecordsService = new PatientsRecordsService(patientRepository, appointmentRecordDtoMapper);
        SearchPatientService searchPatientService = new SearchPatientService(patientRepository, patientDtoMapper, dtoValidator);
        PatientRegistrationService patientRegistrationService = new PatientRegistrationService(patientRepository, registrationPatientConverter, patientMapper, patientDtoMapper, dtoValidator);
        PatientLoginService patientLoginService = new PatientLoginService(patientRepository, patientLoginConverter, patientDtoMapper);
        DoctorChoiceService doctorChoiceService = new DoctorChoiceService(doctorRepository, doctorDtoMapper);
        DoctorInfoService doctorInfoService = new DoctorInfoService(doctorRepository, doctorDtoMapper);
        DoctorsAppointmentsService doctorsAppointmentsService = new DoctorsAppointmentsService(doctorsAppointmentRepository, docAppDtoForPatientMapper);
        BookingDoctorsAppointmentService bookingDoctorsAppointmentService = new BookingDoctorsAppointmentService(doctorsAppointmentRepository, patientRepository, docAppDtoForPatientMapper);
        AdminHomePageService adminHomePageService = new AdminHomePageService();
        DoctorRegistrationService doctorRegistrationService = new DoctorRegistrationService(doctorRepository, workScheduleRepository, registrationDoctorConverter,
                doctorMapper, doctorDtoMapper, dtoValidator);
        NextWeekTimetableService nextWeekTimetableService = new NextWeekTimetableService(doctorRepository, doctorsAppointmentRepository, workScheduleRepository, scheduleAsListDtoMapper);
        TimetableService timetableService = new TimetableService(doctorsAppointmentRepository, docAppDtoForAdminMapper);


        AppointmentRecordCreatePerformer appointmentRecordCreatePerformer = new AppointmentRecordCreatePerformer(appointmentRecordCreateService);
        AppointmentRecordPerformer appointmentRecordPerformer = new AppointmentRecordPerformer(appointmentRecordService, appointmentRecordConverter);
        DoctorLoginPerformer doctorLoginPerformer = new DoctorLoginPerformer(doctorLoginService);
        DoctorLogoutPerformer doctorLogoutPerformer = new DoctorLogoutPerformer();
        MedicCardPerformer medicCardPerformer = new MedicCardPerformer(medicCardService, patientDtoConverter);
        PatientsRecordsPerformer patientsRecordsPerformer = new PatientsRecordsPerformer(patientsRecordsService, appointmentRecordDtoListConverter);
        SearchPatientPerformer searchPatientPerformer = new SearchPatientPerformer(searchPatientService, patientDtoListConverter);
        PatientLoginPerformer patientLoginPerformer = new PatientLoginPerformer(patientLoginService);
        DoctorChoicePerformer doctorChoicePerformer = new DoctorChoicePerformer(doctorChoiceService, doctorDtoListConverter);
        DoctorInfoPerformer doctorInfoPerformer = new DoctorInfoPerformer(doctorInfoService, doctorDtoConverter);
        DoctorsAppointmentsChoicePerformer doctorsAppointmentsPerformer = new DoctorsAppointmentsChoicePerformer(doctorsAppointmentsService, docAppDtoListForPatientConverter);
        BookingDoctorsAppointmentPerformer bookingDoctorsAppointmentPerformer = new BookingDoctorsAppointmentPerformer(bookingDoctorsAppointmentService, docAppDtoForPatientConverter, infoConverter);
        PatientLogoutPerformer patientLogoutPerformer = new PatientLogoutPerformer();
        PatientRegistrationPerformer patientRegistrationPerformer = new PatientRegistrationPerformer(patientRegistrationService);
        AdminHomePagePerformer adminHomePagePerformer = new AdminHomePagePerformer(adminHomePageService, patientRepository, doctorRepository, adminStatisticsDtoConverter);
        DoctorRegistrationPerformer doctorRegistrationPerformer = new DoctorRegistrationPerformer(doctorRegistrationService);
        NextWeekTimetablePerformer nextWeekTimetablePerformer = new NextWeekTimetablePerformer(nextWeekTimetableService);
        TimetablePerformer timetablePerformer = new TimetablePerformer(timetableService, docAppDtoListForAdminConverter);
        AdminLogoutPerformer adminLogoutPerformer = new AdminLogoutPerformer();

        List<Performer> allPerformers = List.of(doctorLoginPerformer, searchPatientPerformer,
                                                medicCardPerformer, appointmentRecordCreatePerformer,
                                                appointmentRecordPerformer, patientsRecordsPerformer,
                                                doctorLogoutPerformer, patientRegistrationPerformer,
                                                patientLoginPerformer, doctorChoicePerformer,
                                                doctorInfoPerformer, doctorsAppointmentsPerformer,
                                                bookingDoctorsAppointmentPerformer, patientLogoutPerformer,
                                                adminHomePagePerformer, doctorRegistrationPerformer,
                                                nextWeekTimetablePerformer, timetablePerformer,
                                                adminLogoutPerformer);
        performerDispatcher = new PerformerDispatcher(doctorLoginPerformer, searchPatientPerformer,
                                                      medicCardPerformer, appointmentRecordCreatePerformer,
                                                      appointmentRecordPerformer, patientsRecordsPerformer,
                                                      doctorLogoutPerformer, patientRegistrationPerformer,
                                                      patientLoginPerformer, doctorChoicePerformer,
                                                      doctorInfoPerformer, doctorsAppointmentsPerformer,
                                                      bookingDoctorsAppointmentPerformer, patientLogoutPerformer,
                                                      adminHomePagePerformer, doctorRegistrationPerformer,
                                                      nextWeekTimetablePerformer, timetablePerformer,
                                                      adminLogoutPerformer, allPerformers);
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
        }catch (MethodNotAllowedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_METHOD_NOT_ALLOWED);
        }catch (PageNotFoundException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_NOT_FOUND);
        }catch (UserAlreadyExistsException
                | DtoValidationException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_BAD_REQUEST);
        }catch (ServerTechnicalProblemsException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_INTERNAL_SERVER_ERROR);
        }catch (NotAuthenticatedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_UNAUTHORIZED);
        }catch (AlreadyBookedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_CONFLICT);
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
        }catch (UserAlreadyExistsException
                | ConstraintViolationException
                | DtoValidationException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_BAD_REQUEST);
        }catch (ServerTechnicalProblemsException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_INTERNAL_SERVER_ERROR);
        }catch (NotAuthenticatedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_UNAUTHORIZED);
        }catch (AlreadyBookedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_CONFLICT);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        }catch (UserAlreadyExistsException
                | ConstraintViolationException
                | DtoValidationException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_BAD_REQUEST);
        }catch (ServerTechnicalProblemsException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_INTERNAL_SERVER_ERROR);
        }catch (NotAuthenticatedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_UNAUTHORIZED);
        }catch (AlreadyBookedException exception){
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_CONFLICT);
        }
    }
}
