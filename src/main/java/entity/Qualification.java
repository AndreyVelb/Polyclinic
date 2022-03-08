package entity;

import org.codehaus.jackson.annotate.JsonProperty;

public enum Qualification {
    @JsonProperty("6")
    SIXTH,
    @JsonProperty("5")
    FIFTH,
    @JsonProperty("4")
    FOURTH,
    @JsonProperty("3")
    THIRD,
    @JsonProperty("2")
    SECOND,
    @JsonProperty("1")
    FIRST;
}
