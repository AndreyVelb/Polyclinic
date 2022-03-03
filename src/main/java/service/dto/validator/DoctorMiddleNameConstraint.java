package service.dto.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoctorMiddleNameValidator.class)
public @interface DoctorMiddleNameConstraint {
    String message() default "Поле ОТЧЕСТВО должно быть либо пустым, либо быть записано киррилическими буквами";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
