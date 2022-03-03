package util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlPath {

    public static final String PATIENT_REGISTRATION = "/join";

    public static final String PATIENT_LOGIN = "/patient/login";

    public static final String PATIENT_LOGOUT = "/patient/logout";

    public static final String PATIENT_CHOOSE_DOCTOR = "/patient/doctors";


    public static final String DOCTOR_LOGIN = "/doctor/login";

    public static final String DOCTOR_LOGOUT = "/doctor/logout";

    public static final String DOCTOR_SEARCH_PATIENT = "/doctor/patients";

    public static final String DOCTOR_CHOOSE_PATIENT = "/doctor/patients/*";
    //   /medic_card        /appointment_res




}
