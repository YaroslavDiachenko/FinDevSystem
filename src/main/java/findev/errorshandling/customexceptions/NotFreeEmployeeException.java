package findev.errorshandling.customexceptions;

public class NotFreeEmployeeException extends RuntimeException {
    public NotFreeEmployeeException(String exception) {
        super(exception);
    }
}
