package com.kevinCarlWalker.web.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Names {
  @JsonProperty
  private Long nameId;
  @JsonProperty
  private String firstName;
  @JsonProperty
  private String lastName;

  public Names(Long nameId, String firstName, String lastName) {
    this.nameId = nameId;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getNameId() {
    return nameId;
  }

  public void setNameId(Long nameId) {
    this.nameId = nameId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
