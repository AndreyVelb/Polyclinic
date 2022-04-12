package exception;

public class AppRecordNotFoundException extends RuntimeException{
    public AppRecordNotFoundException(String message) {
        super(message);
    }
}
