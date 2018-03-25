package findev.config.errorshandling;

public class NotFreeEmployeeException extends RuntimeException {
    public NotFreeEmployeeException(String exception) {
        super(exception);
    }
}
