package com.skrymer.webstub.handler;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class HttpServletRequestStubNameResolver implements StubNameResolver {
  public static final String HTTP_REQUEST_PARAM_STUB = "stub";

  @Override
  public String resolve(HttpServletRequest request) {
    String name = request.getParameter(HTTP_REQUEST_PARAM_STUB);

    if (name != null) return name;

    return "";
  }
}
