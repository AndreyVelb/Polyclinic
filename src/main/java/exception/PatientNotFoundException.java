package exception;

public class PatientNotFoundException extends Exception{
    private final String message;

    public PatientNotFoundException(String lastName) {
        this.message = "Пациент с фамилией " + lastName + " не найден";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
