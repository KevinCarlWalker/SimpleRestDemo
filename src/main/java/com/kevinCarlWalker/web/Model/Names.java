package com.kevinCarlWalker.web.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Names {
  @JsonProperty
  private Integer nameId;
  @JsonProperty
  private String firstName;
  @JsonProperty
  private String lastName;

  public Names() {
    this.nameId = 0;
    this.firstName = "";
    this.lastName = "";
  }

  public Names(Integer nameId, String firstName, String lastName) {
    this.nameId = nameId;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Integer getNameId() {
    return nameId;
  }

  public void setNameId(Integer nameId) {
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
