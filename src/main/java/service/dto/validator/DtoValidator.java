package service.dto.validator;

import exception.DtoValidationException;
import lombok.SneakyThrows;
import service.dto.Dto;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class DtoValidator {

    public void validate(Dto dto) throws ConstraintViolationException {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        var validationResults = validator.validate(dto);
        if (!validationResults.isEmpty()) {
            throw new DtoValidationException("При вводе информации были допущены следующие ошибки: \n" + createValidationResultMessage(validationResults));
        }
    }

    private static String createValidationResultMessage(Set<ConstraintViolation<Dto>> validationResult){
        StringBuilder buffer = new StringBuilder();
        validationResult.forEach(result -> buffer.append(result.getMessage())
                                                 .append("\n"));
        return buffer.toString();
    }
}
