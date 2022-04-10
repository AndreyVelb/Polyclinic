package exception;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException() {
        super("Извините, такого пациента не существует...");
    }
}
