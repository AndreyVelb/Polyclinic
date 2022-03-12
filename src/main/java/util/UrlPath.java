package util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlPath {

    public static final String ADMIN_PATH = "admin";
    public static final String ADMIN_SUBPATH_LOGOUT = "logout";
    public static final String ADMIN_SUBPATH_DOC_REGISTRATION = "doc-registration";
//    public static final String ADMIN_SUBPATH_DOCTORS = "doctors";
//    public static final String ADMIN_SUBPATH_CHANGE_DOCTORS_SCHEDULE = "change-schedule";
    public static final String ADMIN_SUBPATH_CREATE_TIMETABLE = "next-week-timetable";
    public static final String ADMIN_SUBPATH_TIMETABLE = "timetable";


    public static final String DOCTOR_LOGIN = "/doctor/login";
    public static final String DOCTOR_PATH = "doctor";
    public static final String DOCTOR_SUBPATH_LOGOUT = "logout";
    public static final String DOCTOR_SUBPATH_PATIENTS = "patients";
    public static final String DOCTOR_SUBPATH_ALL_PATIENTS_RECORDS = "records";
    public static final String DOCTOR_SUBPATH_WRITING_RECORD = "writing-record";


    public static final String PATIENT_REGISTRATION = "/join";
    public static final String PATIENT_LOGIN = "/patient/login";
    public static final String PATIENT_PATH = "patient";
    public static final String PATIENT_SUBPATH_LOGOUT = "logout";
    public static final String PATIENT_SUBPATH_DOCTORS = "doctors";

}
