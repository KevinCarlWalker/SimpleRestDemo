package com.kevinCarlWalker.web.Model;

import com.kevinCarlWalker.web.exceptions.ExceptionMapper;
import com.kevinCarlWalker.web.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;

public class NamesValidator {
  public static final String ERROR_NULL_OBJECT = "Names object is null";
  public static final String ERROR_INVALID_NAME_ID = "Invalid ID supplied: ";
  public static final String ERROR_MISS_MATCHED_NAME_ID = "Name ID in object does not match what was supplied in the url";
  public static final String ERROR_INVALID_FIRST_NAME = "Invalid First Name supplied: ";

  public static boolean nameIsValid(String name) {
    return name != null && !name.trim().isEmpty();
  }

  private static void errorOut(List<String> errors) {
    throw new BadRequestException(String.join(", ", errors), ExceptionMapper.INVALID_NAME);
  }

  /*
  *  a valid Names object requires a valid id and valid first name
  */
  public static boolean validate(Names name) {
    List<String> errors = new ArrayList<>();

    if (name == null) {
      errors.add(ERROR_NULL_OBJECT);
    } else {
      if (name.getNameId() <= 0) {
        errors.add(ERROR_INVALID_NAME_ID + name.getNameId());
      }
      if (!nameIsValid(name.getFirstName())) {
        errors.add(ERROR_INVALID_FIRST_NAME + name.getFirstName());
      }
    }
    if (!errors.isEmpty()) {
      errorOut(errors);
    }
    return true;
  }
}
