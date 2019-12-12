package com.kevinCarlWalker.web

import com.kevinCarlWalker.web.Model.Names
import com.kevinCarlWalker.web.Model.NamesValidator
import com.kevinCarlWalker.web.exceptions.BadRequestException
import com.kevinCarlWalker.web.exceptions.NotFoundException

import spock.lang.Specification

class NamesApiTest extends Specification {

  NamesApi api = new NamesApi()
  Names validName1

  void setup() {
    validName1 = new Names(34, "Donkey","Kong")
  }

  def "Get should return a Names Object"() {

    when:
    def getMessage = api.getName(validName1.getNameId())
    then:
    noExceptionThrown()
    getMessage == validName1
    getMessage.getNameId() == validName1.getNameId()
  }

  def "Get should throw an exception if nameId is negative"() {
    def negNumber = -43
    when:
    api.getName(negNumber)
    then:
    def exception = thrown(NotFoundException)
    exception.message == NamesValidator.ERROR_INVALID_NAME_ID + negNumber
  }

  def "Get should throw an exception if companyId is not found"() {
    when:
    api.getName(validName1.getNameId())
    then:
    def exception = thrown(NotFoundException)
    exception.message == NamesValidator.ERROR_INVALID_NAME_ID + validName1.getNameId()
  }

  def "Post should insert into Database when all goes well"() {
    when:
    api.postName(validName1)
    then:
    noExceptionThrown()
  }

  def "Put should update Database when all goes well"() {
    when:
    api.putName(validName1.getNameId(), validName1)
    then:
    noExceptionThrown()
  }

  def "Put should throw error if nameId on path and in object don't match" (){
    when:
    api.putName(56, validName1)
    then:
    def exception = thrown(BadRequestException)
    exception.message == NamesValidator.ERROR_MISS_MATCHED_NAME_ID
  }
}
