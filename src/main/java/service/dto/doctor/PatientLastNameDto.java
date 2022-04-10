package service.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.Dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PatientLastNameDto implements Dto {

    @NotNull(message = "Поле ФАМИЛИЯ не должно быть пустым")
    @Size(max = 20, message = "Поле ФАМИЛИЯ не должно превышать 20 символов")
    @Pattern(regexp = "[а-яА-Я-]+", message = "Поле ФАМИЛИЯ должно быть записано кирилическими буквами")
    private String lastName;
}
