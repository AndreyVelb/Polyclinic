package exception;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException() {
        super("Извините, такого доктора не существует...");
    }
}
