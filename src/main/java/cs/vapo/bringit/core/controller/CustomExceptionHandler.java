package cs.vapo.bringit.core.controller;

import cs.vapo.bringit.core.controller.http.CustomHeaders;
import cs.vapo.bringit.core.exceptions.BadRequestException;
import cs.vapo.bringit.core.exceptions.InternalServerException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler(value = {BadRequestException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> badRequestHandler(final Exception badRequestException) {
        final String expMessage = badRequestException.getMessage();
        log.info("Returning 400 status code, triggered by {}, Exception Type: {}", badRequestException.getMessage(), badRequestException.getClass().getName());
        return ResponseEntity.badRequest().header(CustomHeaders.ERROR_MESSAGE_HEADER, expMessage).build();
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> notFoundHandler(final Exception notFoundException) {
        final String expMessage = notFoundException.getMessage();
        return ResponseEntity.notFound().header(CustomHeaders.ERROR_MESSAGE_HEADER, expMessage).build();

    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> conflictHandler(final Exception conflictException) {
        log.error("Conflict exception: {}", conflictException.getMessage(), conflictException);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }

    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<Object> internalServerErrorHandler(final Exception internalException) {
        final String expMessage = internalException.getMessage();
        return ResponseEntity.internalServerError().header(CustomHeaders.ERROR_MESSAGE_HEADER, expMessage).build();
    }
}
