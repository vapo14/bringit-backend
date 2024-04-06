package cs.vapo.bringit.core.controller;

import cs.vapo.bringit.core.controller.headers.CustomHeaders;
import cs.vapo.bringit.core.exceptions.BadRequestException;
import cs.vapo.bringit.core.exceptions.InternalServerException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.invoke.MethodHandles;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> badRequestHandler(final Exception badRequestException) {
        final String expMessage = badRequestException.getMessage();
        return ResponseEntity.badRequest().header(CustomHeaders.ERROR_MESSAGE_HEADER, expMessage).build();
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> notFoundHandler(final Exception notFoundException) {
        final String expMessage = notFoundException.getMessage();
        return ResponseEntity.notFound().header(CustomHeaders.ERROR_MESSAGE_HEADER, expMessage).build();

    }

    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<Object> internalServerErrorHandler(final Exception internalException) {
        final String expMessage = internalException.getMessage();
        return ResponseEntity.internalServerError().header(CustomHeaders.ERROR_MESSAGE_HEADER, expMessage).build();
    }
}
