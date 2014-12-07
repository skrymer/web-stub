package com.skrymer.webstub.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttRequestContext {
  private HttpServletRequest request;
  private HttpServletResponse response;

  public HttRequestContext(HttpServletRequest request, HttpServletResponse response){
    this.request = request;
    this.response = response;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public HttpServletResponse getResponse() {
    return response;
  }
}
