package com.kevinCarlWalker.web.Model

import com.kevinCarlWalker.web.exceptions.BadRequestException
import spock.lang.Specification

class namesValidatorTest extends Specification {
  NamesValidator validator = new NamesValidator()

  Names validName1

  void setup() {
    validName1 = new Names(12, "Princess", "Peach")
  }

  def "NameIsValid should return true on a valid string"() {
    when:
    boolean result1 = validator.nameIsValid(validName1.getLastName())
    boolean result2 = validator.nameIsValid(validName1.getLastName())
    then:
    result1
    result2
  }

  def "NameIsValid should return false on white space input"() {
    when:
    boolean result = validator.nameIsValid("  ")
    then:
    !result
  }

  def "NameIsValid should return false on null input"() {
    when:
    boolean result = validator.nameIsValid(null)
    then:
    !result
  }

  def "Validate should return true on valid input"() {
    when:
    boolean result = validator.validate(validName1)
    then:
    noExceptionThrown()
    result
  }

  def "Validate should throw exception on null input"() {
    when:
    boolean result = validator.validate(null)
    then:
    def exception = thrown(BadRequestException)
    exception.message == NamesValidator.ERROR_NULL_OBJECT
  }

  def "Validate should throw one exception containing multiple messages if multiple errors are found"() {
    Names invalidName1 = new Names(-33, "    ", null)
    when:
    validator.validate(invalidName1)
    then:
    def exception = thrown(BadRequestException)
    exception.message == (NamesValidator.ERROR_INVALID_NAME_ID + invalidName1.getNameId() + ", "
      + NamesValidator.ERROR_INVALID_FIRST_NAME + invalidName1.getFirstName())
  }
}
