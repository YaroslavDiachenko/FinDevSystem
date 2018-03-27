package findev.errorshandling.customexceptions;

public class MissingUserException extends RuntimeException {
    public MissingUserException(String exception) {
        super(exception);
    }
}
