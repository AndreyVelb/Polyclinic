package service.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Objects;

public class RegistrationLocalDateValidator implements ConstraintValidator<DoctorMiddleNameConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value.isAfter(LocalDate.now())
            && value.isBefore(getDateThatEarlierPresentOn(150))){
            return false;
        }
        else return true;
    }

    private LocalDate getDateThatEarlierPresentOn(int numberOfYears){
        return LocalDate.of(LocalDate.now().getYear() - numberOfYears,
               LocalDate.now().getMonth(),
               LocalDate.now().getDayOfMonth());
    }
}
