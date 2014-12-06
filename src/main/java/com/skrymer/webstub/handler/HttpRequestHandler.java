package com.skrymer.webstub.handler;

import com.skrymer.webstub.domain.Stub;
import com.skrymer.webstub.scriptexecutor.ScriptExecutor;
import com.skrymer.webstub.service.StubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by skrymer on 20/06/14.
 */
@Component
public class HttpRequestHandler implements RequestHandler<HttpServletRequest, HttpServletResponse> {
  public static final String HTTP_REQUEST_PARAM_STUB = "stub";
  private StubService stubService;
  private ScriptExecutor scriptExecutor;

  @Autowired
  public HttpRequestHandler(ScriptExecutor scriptExecutor, StubService stubService) {
    this.scriptExecutor = scriptExecutor;
    this.stubService = stubService;
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response) {
    Stub stub = stubService.findStubByName(getStubNameFromRequest(request));

    scriptExecutor.execute(stub.getActiveScript(), request, response);
  }

  private String getStubNameFromRequest(HttpServletRequest request) {
    String name = request.getParameter(HTTP_REQUEST_PARAM_STUB);

    if (name != null) {
      return name;
    }

    return "";
  }
}
