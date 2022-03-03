package service.dto.validator;

import exception.DtoValidationException;
import service.dto.Dto;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class DtoValidator {

    public boolean isValid(Dto dto) throws DtoValidationException {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = (Validator) validatorFactory.getValidator();
        var validationResults = validator.validate(dto);
        if (!validationResults.isEmpty()) {
            throw new DtoValidationException(new ConstraintViolationException(validationResults));
        }
        else {
            return true;
        }
    }
}
