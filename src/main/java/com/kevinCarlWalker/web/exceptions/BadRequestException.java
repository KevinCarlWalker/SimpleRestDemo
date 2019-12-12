package com.kevinCarlWalker.web.exceptions;

public class BadRequestException extends ExceptionWithCode {

  public BadRequestException(String message, int subcode){
    super(message, subcode);
  }
}
