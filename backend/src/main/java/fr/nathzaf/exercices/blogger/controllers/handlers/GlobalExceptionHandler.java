package fr.nathzaf.exercices.blogger.controllers.handlers;

import fr.nathzaf.exercices.blogger.exceptions.CategoryNameAlreadyExistsException;
import fr.nathzaf.exercices.blogger.exceptions.CategoryNotFoundException;
import fr.nathzaf.exercices.blogger.exceptions.PostNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            CategoryNotFoundException.class,
            PostNotFoundException.class
    })
    public ResponseEntity<String> handleNotFoundException(Exception e) {
        LOG.warn("[NOT FOUND] {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(
            CategoryNameAlreadyExistsException.class
    )
    public ResponseEntity<String> handleCategoryNameAlreadyExistsException(Exception e) {
        LOG.warn("[BAD REQUEST] {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
