package service.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class DoctorMiddleNameValidator implements ConstraintValidator<DoctorMiddleNameConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.equals(value, "")){
            return true;
        }
        else if(value == null){
            return true;
        }
        else return value.matches("[а-яА-Я]+");
    }
}
