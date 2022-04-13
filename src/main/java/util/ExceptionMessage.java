package util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessage {

    public static final String DOCTOR_NOT_FOUND = "Доктор не найден";
    public static final String PATIENT_NOT_FOUND = "Пациент не найден";
    public static final String DOC_APPOINTMENT_NOT_FOUND = "Запись к доктору не найдена";
    public static final String ALREADY_BOOKED = "Извините, данная запись уже забронирована. Попробуйте выбрать другую...";
    public static final String APP_RECORD_NOT_FOUND = "Извините, такой записи не существует...";
    public static final String NOT_AUTHENTICATED = "Вы не прошли аутентификацию. Попробуйте еще раз...";
    public static final String NOT_FOUND = "К сожалению по вашему запросу ничего не найдено";
    public static final String SERVER_TECHNICAL_PROBLEMS = "Просим прощения. На сервере технические неполадки. Попробуйте еще раз...";
    public static final String USER_ALREADY_EXISTS = "Пользователь с такими параметрами уже существует. Попробуйте еще раз введя другие данные...";

}
