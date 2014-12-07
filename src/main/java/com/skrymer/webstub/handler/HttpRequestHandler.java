package com.skrymer.webstub.handler;

import com.skrymer.webstub.domain.Stub;
import com.skrymer.webstub.scriptexecutor.ScriptExecutor;
import com.skrymer.webstub.service.StubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by skrymer on 20/06/14.
 */
@Component
public class HttpRequestHandler implements RequestHandler<HttRequestContext> {
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
  public void handle(HttRequestContext context) {
    Stub stub = findStubForRequest(context.getRequest());
    executeActiveScript(stub, context);
  }

  private Stub findStubForRequest(HttpServletRequest request) {
    String name = stubNameResolver.resolve(request);
    return stubService.findStubByName(name);
  }

  private void executeActiveScript(Stub stub, HttRequestContext context) {
    if (stub.getActiveScript() == null) {
      throw new NoActiveScriptIsSetException("No active script is set for stub " + stub.getName());
    }

    scriptExecutor.execute(stub.getActiveScript(), context.getRequest(), context.getResponse());
  }
}
