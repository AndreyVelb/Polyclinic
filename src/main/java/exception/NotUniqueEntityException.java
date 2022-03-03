package exception;

public class NotUniqueEntityException extends Exception{
    private final String message;

    public NotUniqueEntityException(String className, String parameter) {
        this.message = "Entity " + className + " with parameter " + parameter + " already exists. Entity " + className +
                " must be unique";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
