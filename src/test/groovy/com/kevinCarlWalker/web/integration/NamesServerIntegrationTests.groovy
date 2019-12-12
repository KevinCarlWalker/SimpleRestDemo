package com.kevinCarlWalker.web.integration

import com.kevinCarlWalker.web.Model.Names
import com.kevinCarlWalker.web.Model.NamesValidator
import com.kevinCarlWalker.web.exceptions.ExceptionMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration", inheritProfiles = false)
@Import(IntegrationTestConfig.class)
class NamesServerIntegrationTests extends Specification {
  @Autowired
  TestRestTemplate restTemplate

  Names validName1

  def setupSpec() {
    def environment = [:] as Map
    environment.putAll(System.getenv())
  }

  void setup() {
    def uniquePositiveLong = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE
    validName1 = new Names(uniquePositiveLong, "Super", "Mario")
  }

  def "Post Name without a First Name. Should return 400 and provide explanations"() {
    validName1.setFirstName("")
    when:
    def result = restTemplate.exchange("/names", HttpMethod.POST, new HttpEntity<>(validName1), Map)

    then:
    result.statusCode == HttpStatus.BAD_REQUEST
    result.body.get(ExceptionMapper.ERROR_CODE) == String.valueOf(ExceptionMapper.INVALID_NAME)
    result.body.get(ExceptionMapper.ERROR_MESSAGE).contains(NamesValidator.ERROR_INVALID_FIRST_NAME)
  }

  def "Post to create a name, then Get it and make sure they match"() {
    restTemplate.exchange("/names", HttpMethod.POST, new HttpEntity<>(validName1), Map)

    when:
    def result = restTemplate.exchange("/names/" + validName1.getNameId(), HttpMethod.GET, new HttpEntity<>(""), Names)
    def resultBody = result.body

    then:
    resultBody.getNameId() == validName1.getNameId()
    resultBody.getFirstName() == validName1.getFirstName()
    resultBody.getLastName() == validName1.getLastName()
  }

  def "Second post should work like a PUT. Post twice, Get data to validate second post info was saved"() {
    restTemplate.exchange("/names", HttpMethod.POST, new HttpEntity<>(validName1), Map)
    validName1.setFirstName("Luigi")

    when:
    restTemplate.exchange("/names", HttpMethod.POST, new HttpEntity<>(validName1), Map)
    def result = restTemplate.exchange("/names/" + validName1.getNameId(), HttpMethod.GET, new HttpEntity<>(""), Names)
    def resultBody = result.body

    then:
    resultBody.getNameId() == validName1.getNameId()
    resultBody.getFirstName() == validName1.getFirstName()
    resultBody.getLastName() == validName1.getLastName()
  }

  def "Create a name (Post), update it(Put), then retrieve it(Get) and validate that the update worked"() {
    restTemplate.exchange("/names", HttpMethod.POST, new HttpEntity<>(validName1), Map)

    validName1.setLastName("Wario")
    restTemplate.exchange("/names/" + validName1.getNameId(), HttpMethod.PUT, new HttpEntity<>(validName1), Map)

    when:
    def result = restTemplate.exchange("/names/" + validName1.getNameId(), HttpMethod.GET, new HttpEntity<>(""), Names)
    def resultBody = result.body

    then:
    resultBody.getNameId() == validName1.getNameId()
    resultBody.getFirstName() == validName1.getFirstName()
    resultBody.getLastName() == validName1.getLastName()
  }
}
