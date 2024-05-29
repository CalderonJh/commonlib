package dev.jhonc.lib.demolib.controller.advice;

import dev.jhonc.lib.demolib.exception.NoDataException;
import dev.jhonc.lib.demolib.exception.ValidationException;
import dev.jhonc.lib.demolib.exception.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.io.Serial;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionApi extends Exception {
  @Serial private static final long serialVersionUID = -84030083810250L;
  public static final String WARNING = "WARNING";
  public static final String ERROR = "ERROR";

  @ExceptionHandler({ValidationException.class, NoDataException.class})
  public ResponseEntity<ErrorDTO> handleControlledExceptions(
      Exception e, HttpServletRequest request) {
    var badRequestStatus = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(
        new ErrorDTO(badRequestStatus.value(), WARNING, e.getMessage(), request.getRequestURI()),
        badRequestStatus);
  }

  @ExceptionHandler({
    NullPointerException.class,
    ConstraintViolationException.class,
    UnexpectedRollbackException.class,
    java.sql.SQLIntegrityConstraintViolationException.class,
    InvalidDataAccessApiUsageException.class
  })
  public ResponseEntity<ErrorDTO> handleNoControlledExceptions(
      Exception e, HttpServletRequest request) {
    var conflictStatus = HttpStatus.CONFLICT;
    return new ResponseEntity<>(
        new ErrorDTO(
            conflictStatus.value(),
            ERROR + ": " + e.getClass().getSimpleName(),
            e.getMessage(),
            request.getRequestURI()),
        conflictStatus);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorDTO> handleNoControlledValidationsExceptions(
      HttpServletRequest request, BindingResult bindingResult) {
    final HttpStatus conflictStatus = HttpStatus.CONFLICT;
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    var errorMessage = new StringBuilder();
    fieldErrors.forEach(
        err -> {
          var field = err.getField();
          var msg = err.getDefaultMessage();
          errorMessage.append("(").append(field).append(") -> ").append(msg).append("; ");
        });

    return new ResponseEntity<>(
        new ErrorDTO(
            conflictStatus.value(), ERROR, errorMessage.toString(), request.getRequestURI()),
        conflictStatus);
  }
}
