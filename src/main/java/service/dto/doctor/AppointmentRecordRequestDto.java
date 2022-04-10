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
public class AppointmentRecordRequestDto implements Dto {

    @NotNull
    private Long patientId;

    @NotNull(message = "Поле ЖАЛОБЫ ПАЦИЕНТА не должно быть пустым")
    @Size(max = 500, message = "Поле ЖАЛОБЫ ПАЦИЕНТА не должно превышать 500 символов")
    @Pattern(regexp = "[а-яА-Я ]+", message = "Поле ЖАЛОБЫ ПАЦИЕНТА должно быть записано кирилическими буквами")
    private String healthComplaints;

    @NotNull(message = "Поле РЕКОМЕНДАЦИИ ВРАЧА не должно быть пустым")
    @Size(max = 499, message = "Поле РЕКОМЕНДАЦИИ ВРАЧА не должно превышать 499 символов")
    @Pattern(regexp = "[а-яА-Я ]+", message = "Поле РЕКОМЕНДАЦИИ ВРАЧА должно быть записано кирилическими буквами")
    private String doctorsRecommendation;
}
