package config;

import exception.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.vladmihalcea.hibernate.type.util.Configuration.APPLICATION_PROPERTIES_FILE_NAME;

public class Config {
    public static final String DOCTOR_NOT_FOUND_MESSAGE = "exception.message.DoctorNotFoundExMessage";
    public static final String PATIENT_NOT_FOUND_MESSAGE = "exception.message.PatientNotFoundExMessage";
    private static final String DOC_APPOINTMENT_NOT_FOUND_MESSAGE = "exception.message.DocAppointmentNotFoundExMessage";
    private static final String ALREADY_BOOKED_MESSAGE = "exception.message.AlreadyBookedExMessage";
    private static final String APP_RECORD_NOT_FOUND_MESSAGE = "exception.message.AppRecordNotFoundExMessage";
    private static final String NOT_AUTHENTICATED_MESSAGE = "exception.message.NotAuthenticatedExMessage";
    private static final String NOT_FOUND_MESSAGE = "exception.message.NotFoundExMessage";
    private static final String SERVER_TECHNICAL_PROBLEMS_MESSAGE = "exception.message.ServerTechnicalProblemsExMessage";
    private static final String USER_ALREADY_EXISTS_MESSAGE = "exception.message.UserAlreadyExistsExMessage";



    private final Properties config = new Properties();

    public Config() {
        InputStream input = Config.class.getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES_FILE_NAME);
        try {
            config.load(input);
        } catch (IOException e) {
            throw new ConfigException(e.getMessage());
        }
    }

    public String getDoctorNotFoundExMessage() {
        return config.getProperty(DOCTOR_NOT_FOUND_MESSAGE);
    }

    public String getPatientNotFoundExMessage() {
        return config.getProperty(PATIENT_NOT_FOUND_MESSAGE);
    }

    public String getDocAppointmentNotFoundExMessage() {
        return config.getProperty(DOC_APPOINTMENT_NOT_FOUND_MESSAGE);
    }

    public String getAlreadyBookedExMessage() {
        return config.getProperty(ALREADY_BOOKED_MESSAGE);
    }

    public String getAppRecordNotFoundExMessage() {
        return config.getProperty(APP_RECORD_NOT_FOUND_MESSAGE);
    }

    public  String getNotAuthenticatedExMessage() {
        return config.getProperty(NOT_AUTHENTICATED_MESSAGE);
    }

    public  String getNotFoundExMessage() {
        return config.getProperty(NOT_FOUND_MESSAGE);
    }

    public  String getServerTechnicalProblemsExMessage() {
        return config.getProperty(SERVER_TECHNICAL_PROBLEMS_MESSAGE);
    }

    public  String getUserAlreadyExistsExMessage() {
        return config.getProperty(USER_ALREADY_EXISTS_MESSAGE);
    }
}
