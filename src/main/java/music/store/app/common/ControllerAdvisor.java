package music.store.app.common;

import music.store.app.common.exceptions.ResourceNotFoundException;
import music.store.app.common.models.ValidationError;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ProblemDetail> handle(ResourceNotFoundException ex) {
        ProblemDetail details = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        details.setTitle(HttpStatus.NOT_FOUND.name());

        return ResponseEntity.of(details).build();
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail details = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid arguments have been provided");
        details.setTitle(HttpStatus.BAD_REQUEST.name());

        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        details.setProperty("errors", errors);

        return ResponseEntity.of(details).build();
    }
}
