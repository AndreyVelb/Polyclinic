package entity;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonValue;

public enum DoctorSpeciality {

    CHIEF_DOCTOR("глав-врач"),

    SURGEON("хирург"),

    GENERAL_DOCTOR("терапевт"),

    DENTIST("стоматолог"),

    OPTOMETRIST("окулист"),

    NEUROLOGIST("невролог");

    private String speciality;

    DoctorSpeciality(String speciality){
        this.speciality = speciality;
    }

    @JsonValue
    public String getSpeciality(){
        return speciality;
    }

}
