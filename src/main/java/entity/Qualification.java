package entity;

import org.codehaus.jackson.annotate.JsonValue;

public enum Qualification {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6);

    private final Integer qualification;

    Qualification(Integer qualification){
        this.qualification = qualification;
    }

    @JsonValue
    public Integer getQualification(){
        return qualification;
    }
}
