package service.dto.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegistrationLocalDateValidator.class)
public @interface RegistrationLocalDateConstraint {
    String message() default "Поле ДАТА РОЖДЕНИЯ должна быть не менее чем 150 лет назад от текущей даты, и не более текущей даты";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
