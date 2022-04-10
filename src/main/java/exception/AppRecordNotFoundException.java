package exception;

public class AppRecordNotFoundException extends RuntimeException{
    public AppRecordNotFoundException() {
        super("Извините, такой записи не существует...");
    }
}
