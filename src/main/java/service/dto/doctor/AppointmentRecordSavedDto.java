package service.dto.doctor;

import entity.Doctor;
import entity.Patient;
import lombok.*;
import service.dto.Dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentRecordSavedDto implements Dto {

    Patient patient;

    Doctor doctor;

    LocalDate visitDate;

    @NotNull(message = "Поле ЖАЛОБЫ ПАЦИЕНТА не должно быть пустым")
    @Size(max = 500, message = "Поле ФАМИЛИЯ не должно превышать 500 символов")
    @Pattern(regexp = "[а-яА-Я-]+", message = "Поле ФАМИЛИЯ должно быть записано кирилическими буквами")
    String healthComplaints;

    @NotNull(message = "Поле РЕКОМЕНДАЦИИ ВРАЧА не должно быть пустым")
    @Size(max = 499, message = "Поле ФАМИЛИЯ не должно превышать 499 символов")
    @Pattern(regexp = "[а-яА-Я-]+", message = "Поле ФАМИЛИЯ должно быть записано кирилическими буквами")
    String doctorsRecommendation;
}
