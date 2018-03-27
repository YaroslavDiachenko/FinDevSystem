package findev.errorshandling.customexceptions;

public class OccupiedNameException extends RuntimeException {
    public OccupiedNameException(String exception) {
        super(exception);
    }
}
