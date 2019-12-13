package com.kevinCarlWalker.web

import com.kevinCarlWalker.web.Model.Names
import com.kevinCarlWalker.web.Model.NamesValidator
import com.kevinCarlWalker.web.exceptions.BadRequestException
import com.kevinCarlWalker.web.exceptions.NotFoundException
import spock.lang.Specification

import javax.naming.event.NamespaceChangeListener

class NamesApiTest extends Specification {

  NamesApi api = new NamesApi()
  Names validName
  Names emptyName
  Names newName

  void setup() {
    validName = new Names(34, "Donkey","Kong")
    emptyName = new Names()
    newName = new Names(0, "Diddy", "Kong")
  }

  def "Post to create an object, the Get it and make sure it matches"() {
    when:
    def postMessage = api.postName(newName)
    def getMessage = api.getName(postMessage.getNameId())
    then:
    noExceptionThrown()
    getMessage.getFirstName() == postMessage.getFirstName()
    getMessage.getLastName() == postMessage.getLastName()
  }

  def "Get should throw an exception if nameId is negative"() {
    def negNumber = -43
    when:
    api.getName(negNumber)
    then:
    def exception = thrown(NotFoundException)
    exception.message == NamesValidator.ERROR_INVALID_NAME_ID + negNumber
  }

  def "Post should insert into Database when object doesn't have an ID"() {
    when:
    def postName = api.postName(newName)
    then:
    noExceptionThrown()
    postName.getFirstName() == newName.getFirstName()
    postName.getLastName() == newName.getLastName()
    postName.getNameId() > 0
  }

  def "Post a Name, and update with Put. UpdateName should be called once, updates should be saved"() {
    when:
    def postName = api.postName(newName)
    def updatedName = new Names(postName.getNameId(), "King", "Kong")
    def putName = api.putName(postName.getNameId(), updatedName)
    then:
    noExceptionThrown()
    putName.getFirstName() == updatedName.getFirstName()
    putName.getLastName() == updatedName.getLastName()
    putName.getNameId() == postName.getNameId()
  }

  def "Put should throw error if nameId on path and in object don't match" (){
    when:
    api.putName(1, validName)
    then:
    def exception = thrown(BadRequestException)
    exception.message == NamesValidator.ERROR_MISS_MATCHED_NAME_ID
  }

  def "Post should create a new name, Delete should delete it"(){
    when:
    def postName = api.postName(newName)
    api.deleteName( postName.getNameId() )
    api.getName(postName.getNameId())
    then:
    def exception = thrown(NotFoundException)
    postName.getFirstName() == newName.getFirstName()
    postName.getLastName() == newName.getLastName()
    postName.getNameId() > 0
    exception.message == NamesValidator.ERROR_INVALID_NAME_ID + postName.getNameId()
  }
}
