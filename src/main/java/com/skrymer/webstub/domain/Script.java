package com.skrymer.webstub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.codec.binary.Base64;

public class Script {
  private Integer id;
  private String name, content;

  public static String encode(String content){
    return new String(Base64.encodeBase64(content.getBytes()));
  }

  public Script() {
  }

  public Script(Integer id, String name, String content) {
    this.id = id;
    this.name = name;
    this.content = content;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getContent() {
    return content;
  }

  @JsonIgnore
  public String getDecodedContent() {
    return new String(Base64.decodeBase64(content));
  }

  @Override
  public int hashCode() {
    return this.name.hashCode() + content.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Script)) {
      return false;
    }

    Script compareTo = (Script) other;

    if (this.name.equals(compareTo.getName()) && this.content.equals(compareTo.getContent())) {
      return true;
    }

    return false;
  }

  @Override
  public String toString() {
    return new StringBuilder().append("Name: ")
        .append(name).toString();
  }
}
