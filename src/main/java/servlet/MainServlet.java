package servlet;

import exception.MethodNotAllowedException;
import exception.PageNotFoundException;
import exception.ServerTechnicalProblemsException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import repository.AppointmentRecordRepository;
import repository.DoctorRepository;
import repository.DoctorsAppointmentRepository;
import repository.PatientRepository;
import repository.WorkScheduleRepository;
import service.Mapper;
import service.admin.AdminHomePageService;
import service.admin.DoctorRegistrationService;
import service.admin.NextWeekTimetableService;
import service.admin.TimetableService;
import service.doctor.AppointmentRecordCreateService;
import service.doctor.AppointmentRecordService;
import service.doctor.DoctorLoginService;
import service.doctor.MedicCardService;
import service.doctor.PatientsRecordsService;
import service.doctor.SearchPatientService;
import service.dto.validator.DtoValidator;
import service.patient.BookingDoctorsAppointmentService;
import service.patient.DoctorChoiceService;
import service.patient.DoctorInfoService;
import service.patient.DoctorsAppointmentsService;
import service.patient.PatientLoginService;
import service.patient.PatientRegistrationService;
import servlet.performer.Performer;
import servlet.performer.PerformerDispatcher;
import servlet.performer.admin.AdminHomePagePerformer;
import servlet.performer.admin.AdminLogoutPerformer;
import servlet.performer.admin.DoctorRegistrationPerformer;
import servlet.performer.admin.NextWeekTimetablePerformer;
import servlet.performer.admin.TimetablePerformer;
import servlet.performer.doctor.AppointmentRecordCreatePerformer;
import servlet.performer.doctor.AppointmentRecordPerformer;
import servlet.performer.doctor.DoctorLoginPerformer;
import servlet.performer.doctor.DoctorLogoutPerformer;
import servlet.performer.doctor.MedicCardPerformer;
import servlet.performer.doctor.PatientsRecordsPerformer;
import servlet.performer.doctor.SearchPatientPerformer;
import servlet.performer.patient.BookingDoctorsAppointmentPerformer;
import servlet.performer.patient.DoctorChoicePerformer;
import servlet.performer.patient.DoctorInfoPerformer;
import servlet.performer.patient.DoctorsAppointmentsChoicePerformer;
import servlet.performer.patient.PatientLoginPerformer;
import servlet.performer.patient.PatientLogoutPerformer;
import servlet.performer.patient.PatientRegistrationPerformer;
import servlet.response.ExceptionResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

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

        Mapper mapper = new Mapper();

        AppointmentRecordCreateService appointmentRecordCreateService = new AppointmentRecordCreateService(patientRepository, appointmentRecordRepository,
                doctorRepository, dtoValidator);
        AppointmentRecordService appointmentRecordService = new AppointmentRecordService(appointmentRecordRepository, mapper);
        DoctorLoginService doctorLoginService = new DoctorLoginService(doctorRepository, objectMapper, mapper);
        MedicCardService medicCardService = new MedicCardService(patientRepository, mapper);
        PatientsRecordsService patientsRecordsService = new PatientsRecordsService(patientRepository, mapper);
        SearchPatientService searchPatientService = new SearchPatientService(patientRepository, mapper, dtoValidator);
        PatientRegistrationService patientRegistrationService = new PatientRegistrationService(patientRepository, objectMapper, mapper, dtoValidator);
        PatientLoginService patientLoginService = new PatientLoginService(patientRepository, objectMapper, mapper);
        DoctorChoiceService doctorChoiceService = new DoctorChoiceService(doctorRepository, mapper);
        DoctorInfoService doctorInfoService = new DoctorInfoService(doctorRepository, mapper);
        DoctorsAppointmentsService doctorsAppointmentsService = new DoctorsAppointmentsService(doctorsAppointmentRepository, mapper);
        BookingDoctorsAppointmentService bookingDoctorsAppointmentService = new BookingDoctorsAppointmentService(doctorsAppointmentRepository, patientRepository, mapper);
        AdminHomePageService adminHomePageService = new AdminHomePageService();
        DoctorRegistrationService doctorRegistrationService = new DoctorRegistrationService(doctorRepository, workScheduleRepository, objectMapper, mapper, dtoValidator);
        NextWeekTimetableService nextWeekTimetableService = new NextWeekTimetableService(doctorsAppointmentRepository, workScheduleRepository, mapper);
        TimetableService timetableService = new TimetableService(doctorsAppointmentRepository, mapper);

        AppointmentRecordCreatePerformer appointmentRecordCreatePerformer = new AppointmentRecordCreatePerformer(appointmentRecordCreateService, objectMapper);
        AppointmentRecordPerformer appointmentRecordPerformer = new AppointmentRecordPerformer(appointmentRecordService, objectMapper);
        DoctorLoginPerformer doctorLoginPerformer = new DoctorLoginPerformer(doctorLoginService);
        DoctorLogoutPerformer doctorLogoutPerformer = new DoctorLogoutPerformer();
        MedicCardPerformer medicCardPerformer = new MedicCardPerformer(medicCardService, objectMapper);
        PatientsRecordsPerformer patientsRecordsPerformer = new PatientsRecordsPerformer(patientsRecordsService, objectMapper);
        SearchPatientPerformer searchPatientPerformer = new SearchPatientPerformer(searchPatientService, objectMapper);
        PatientLoginPerformer patientLoginPerformer = new PatientLoginPerformer(patientLoginService);
        DoctorChoicePerformer doctorChoicePerformer = new DoctorChoicePerformer(doctorChoiceService, objectMapper);
        DoctorInfoPerformer doctorInfoPerformer = new DoctorInfoPerformer(doctorInfoService, objectMapper);
        DoctorsAppointmentsChoicePerformer doctorsAppointmentsPerformer = new DoctorsAppointmentsChoicePerformer(doctorsAppointmentsService, objectMapper);
        BookingDoctorsAppointmentPerformer bookingDoctorsAppointmentPerformer = new BookingDoctorsAppointmentPerformer(bookingDoctorsAppointmentService, objectMapper);
        PatientLogoutPerformer patientLogoutPerformer = new PatientLogoutPerformer();
        PatientRegistrationPerformer patientRegistrationPerformer = new PatientRegistrationPerformer(patientRegistrationService);
        AdminHomePagePerformer adminHomePagePerformer = new AdminHomePagePerformer(adminHomePageService, patientRepository, doctorRepository, objectMapper);
        DoctorRegistrationPerformer doctorRegistrationPerformer = new DoctorRegistrationPerformer(doctorRegistrationService);
        NextWeekTimetablePerformer nextWeekTimetablePerformer = new NextWeekTimetablePerformer(nextWeekTimetableService);
        TimetablePerformer timetablePerformer = new TimetablePerformer(timetableService, objectMapper);
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
        performerDispatcher = new PerformerDispatcher(allPerformers);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
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
            } else {
                throw new PageNotFoundException();
            }
        } catch (MethodNotAllowedException exception) {
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_METHOD_NOT_ALLOWED);
        } catch (PageNotFoundException exception) {
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_NOT_FOUND);
        } catch (ServerTechnicalProblemsException exception) {
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_INTERNAL_SERVER_ERROR);
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
                } else {
                    throw new MethodNotAllowedException();
                }
            } else {
                throw new PageNotFoundException();
            }
        } catch (MethodNotAllowedException exception) {
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_METHOD_NOT_ALLOWED);
        } catch (PageNotFoundException exception) {
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_NOT_FOUND);
        } catch (ServerTechnicalProblemsException exception) {
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            } else {
                throw new PageNotFoundException();
            }
        } catch (MethodNotAllowedException exception) {
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_METHOD_NOT_ALLOWED);
        } catch (PageNotFoundException exception) {
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_NOT_FOUND);
        } catch (ServerTechnicalProblemsException exception) {
            new ExceptionResponse().send(resp.getWriter(), resp, exception, SC_INTERNAL_SERVER_ERROR);
        }
    }
}
