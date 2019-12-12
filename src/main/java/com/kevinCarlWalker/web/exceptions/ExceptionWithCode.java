package com.kevinCarlWalker.web.exceptions;

public class ExceptionWithCode extends RuntimeException{
  private final int subcode;

  ExceptionWithCode(String message, int subcode){
    super(message);
    this.subcode = subcode;
  }

  public int getSubcode(){
    return subcode;
  }
}
