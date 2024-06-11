package com.adepuu.montrackbackend.exceptions;

import com.adepuu.montrackbackend.responses.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.UnknownHostException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(DataNotFoundException.class)
  public final ResponseEntity<Response<String>> handleProductNotFoundException(DataNotFoundException ex){
    return Response.failed(HttpStatus.NOT_FOUND.value(), ex.getMessage());
  }
  @ExceptionHandler(ApplicationException.class)
  public final ResponseEntity<Response<String>> handleProductNotFoundException(ApplicationException ex){
    return Response.failed(HttpStatus.BAD_REQUEST.value(), "Unable to process the request", ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<Response<String>> handleValidationExceptions(MethodArgumentNotValidException ex){
    String errorMessage = ex.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
    return Response.failed(HttpStatus.BAD_REQUEST.value(), "Unable to process the request. Error: " + errorMessage);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Response<String>> handleAllExceptions(Exception ex) {

    log.error(ex.getMessage(), ex.getCause(), ex);

    if (ex.getCause() instanceof UnknownHostException) {
      return Response.failed(HttpStatus.BAD_REQUEST.value(), "Unable to process the request: " + ex.getLocalizedMessage());
    }

    return Response.failed(HttpStatus.BAD_REQUEST.value(), "Unable to process the request: " + ex.getMessage());
  }
}