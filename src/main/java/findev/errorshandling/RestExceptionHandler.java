package findev.errorshandling;

import findev.errorshandling.customexceptions.MissingUserException;
import findev.errorshandling.customexceptions.NotFreeEmployeeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFreeEmployeeException.class)
    protected ResponseEntity<ApiError> handleNotFreeEmployee(NotFreeEmployeeException ex) {
        ApiError err = new ApiError();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingUserException.class)
    protected ResponseEntity<ApiError> handleMissingUser(MissingUserException ex) {
        ApiError err = new ApiError();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
