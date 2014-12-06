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
  private StubService stubService;
  private ScriptExecutor scriptExecutor;
  private StubNameResolver stubNameResolver;

  @Autowired
  public HttpRequestHandler(ScriptExecutor scriptExecutor, StubService stubService, StubNameResolver stubNameResolver) {
    this.scriptExecutor = scriptExecutor;
    this.stubService = stubService;
    this.stubNameResolver = stubNameResolver;
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response) {
    Stub stub = findStubForRequest(request);
    executeActiveScript(stub, request, response);
  }

  private Stub findStubForRequest(HttpServletRequest request) {
    String name = stubNameResolver.resolve(request);

    return stubService.findStubByName(name);
  }

  private void executeActiveScript(Stub stub, HttpServletRequest request, HttpServletResponse response) {
    if (stub.getActiveScript() == null) {
      throw new NoActiveScriptIsSetException("No active script is set for stub " + stub.getName());
    }

    scriptExecutor.execute(stub.getActiveScript(), request, response);
  }
}
