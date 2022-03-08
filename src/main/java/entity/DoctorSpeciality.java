package entity;

import org.codehaus.jackson.annotate.JsonProperty;

public enum DoctorSpeciality {
    @JsonProperty("глав-врач")
    CHIEF_DOCTOR,
    @JsonProperty("хирург")
    SURGEON,
    @JsonProperty("терапевт")
    GENERAL_DOCTOR,
    @JsonProperty("стоматолог")
    DENTIST,
    @JsonProperty("окулист")
    OPTOMETRIST,
    @JsonProperty("невролог")
    NEUROLOGIST;
}
