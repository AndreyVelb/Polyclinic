package exception;

public class DocAppointmentNotFoundException extends RuntimeException{
    public DocAppointmentNotFoundException(String message){
        super(message);
    }
}
