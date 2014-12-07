package com.skrymer.webstub.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: skrymer
 * Date: 7/12/14
 * Time: 5:50 PM
 * To change this template use File | Settings | File Templates.
 */
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
