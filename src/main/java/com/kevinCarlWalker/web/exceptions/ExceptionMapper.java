package com.kevinCarlWalker.web.exceptions;


import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionMapper {
  public static final int INVALID_NAME = 4001;
  public static final String ERROR_MESSAGE = "errorMessage";
  public static final String ERROR_CODE = "errorCode";

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapper.class);

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity handleNotFoundException(NotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.APPLICATION_JSON)
        .body(ImmutableMap.of(ERROR_MESSAGE, exception.getMessage(), ERROR_CODE, String.valueOf(exception.getSubcode())));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity handleBadRequestException(BadRequestException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(ImmutableMap.of(ERROR_MESSAGE, exception.getMessage(), ERROR_CODE, String.valueOf(exception.getSubcode())));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity handleRuntimeException(RuntimeException ex) {
    LOGGER.error(ex.getLocalizedMessage(), ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ex.getLocalizedMessage());
  }

}

