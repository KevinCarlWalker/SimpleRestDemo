package com.kevinCarlWalker.web.exceptions;

public class NotFoundException extends ExceptionWithCode {
  public NotFoundException(String message, int subcode){
    super(message, subcode);
  }
}
