package music.store.app.common.web;

import music.store.app.common.exceptions.ResourceNotFoundException;
import music.store.app.common.web.models.ApiError;
import music.store.app.common.web.models.BaseErrorResult;
import music.store.app.common.web.models.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseErrorResult> handleException(Exception ex) {
        var error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage());

        return new ResponseEntity<>(new BaseErrorResult(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseErrorResult> handleResourceNotFound(ResourceNotFoundException ex) {
        var error = new ApiError(HttpStatus.NOT_FOUND.name(), ex.getMessage());

        return new ResponseEntity<>(new BaseErrorResult(error), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
       var details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

       var error = new ApiError(HttpStatus.BAD_REQUEST.name(), "Invalid arguments have been provided", details);

        return new ResponseEntity<>(new BaseErrorResult(error), HttpStatus.BAD_REQUEST);
    }
}
