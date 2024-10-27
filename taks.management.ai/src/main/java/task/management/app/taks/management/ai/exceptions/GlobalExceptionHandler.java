package task.management.app.taks.management.ai.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }
}
