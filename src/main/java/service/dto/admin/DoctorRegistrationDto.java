package service.dto.admin;

import entity.DoctorSpeciality;
import entity.Qualification;
import lombok.*;
import service.dto.validator.DoctorMiddleNameConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DoctorRegistrationDto {

    @NotNull(message = "Поле ФАМИЛИЯ не должно быть пустым")
    @Size(max = 20, message = "Поле ФАМИЛИЯ не должно превышать 20 символов")
    @Pattern(regexp = "[а-яА-Я-]+", message = "Поле ФАМИЛИЯ должно быть записано кирилическими буквами")
    String lastName;

    @NotNull(message = "Поле ИМЯ не должно быть пустым")
    @Pattern(regexp = "[а-яА-Я-]+", message = "Поле ИМЯ должно быть записано кирилическими буквами")
    @Size(max = 20, message = "Поле ИМЯ не должно превышать 20 символов")
    String firstName;

    @DoctorMiddleNameConstraint
    @Size(max = 20, message = "Поле ОТЧЕСТВО не должно превышать 20 символов")
    String middleName;

    @NotNull(message = "Поле КВАЛИФИКАЦИЯ не должно быть пустым")
    Qualification qualification;

    @NotNull(message = "Поле СПЕЦИАЛЬНОСТЬ не должно быть пустым")
    DoctorSpeciality speciality;

    @NotNull(message = "Поле ЛОГИН не должно быть пустым")
    @Pattern(regexp = "[a-zA-Z01-9]+", message = "Поле ЛОГИН должно быть записано цифрами и(или) латинскими буквами")
    @Size(max = 30, message = "Поле ЛОГИН не должно превышать 30 символов")
    String login;

    @NotNull(message = "Поле ПАРОЛЬ не должно быть пустым")
    @Pattern(regexp = "[a-zA-Z01-9]+", message = "Поле ПАРОЛЬ должно быть записано цифрами и(или) латинскими буквами")
    @Size(max = 30, message = "Поле ПАРОЛЬ не должно превышать 30 символов")
    String password;
}
