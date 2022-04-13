package util;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SessionRole {
    @Getter
    private static final String DOCTOR = "DOCTOR";
    @Getter
    private static final String ADMIN = "ADMIN";
    @Getter
    private static final String PATIENT = "PATIENT";
}
