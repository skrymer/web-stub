package com.skrymer.webstub.scriptexecutor;

import com.skrymer.webstub.domain.Script;
import com.skrymer.webstub.handler.HttRequestContext;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.skrymer.webstub.util.SyntacticSugar.throwIllegalArgumentExceptionIfNull;

@Component
public class GroovyScriptExecutor implements ScriptExecutor<HttRequestContext> {
  public static final String BINDING_VAR_REQUEST = "request";
  public static final String BINDING_VAR_RESPONSE = "response";
  public static final String BINDING_VAR_APPLICATION = "application";
  public static final String BINDING_VAR_OUT = "out";
  public static final String BINDING_VAR_LOG = "log";

  private static final Log LOG = LogFactory.getLog(GroovyScriptExecutor.class);

  @Override
  public void execute(Script script, HttRequestContext context) {
    checkParameters(script, context);

    try {
      Binding bindings = createBindings(script, context);
      executeScript(script, bindings);
    }
    catch (Exception e) {
      handleException(e, context);
    }
  }

  private void checkParameters(Script script, HttRequestContext context) {
    throwIllegalArgumentExceptionIfNull(script, "script");
    throwIllegalArgumentExceptionIfNull(context.getRequest(), "request");
    throwIllegalArgumentExceptionIfNull(context.getResponse(), "response");
  }

  private Binding createBindings(Script script, HttRequestContext context) throws IOException {
    Binding binding = new Binding();

    binding.setVariable(BINDING_VAR_REQUEST, context.getRequest());
    binding.setVariable(BINDING_VAR_RESPONSE, context.getResponse());
    binding.setVariable(BINDING_VAR_APPLICATION, context.getRequest().getServletContext());
    binding.setVariable(BINDING_VAR_OUT, context.getResponse().getOutputStream());
    binding.setVariable(BINDING_VAR_LOG, LogFactory.getLog(script.getName()));

    return binding;
  }

  private void executeScript(Script script, Binding bindings) {
    throwScriptContentIsEmptyExceptionIfEmpty(script);

    new GroovyShell(bindings).evaluate(script.getDecodedContent());
  }

  private void throwScriptContentIsEmptyExceptionIfEmpty(Script script) {
    if (script.getContent() == null || script.getContent().isEmpty()) {
      throw new ScriptContentIsEmptyException("Script content is empty for script: " + script.getName());
    }
  }

  private void handleException(Exception e, HttRequestContext context) {
    LOG.error("Could not execute script", e);

    if (e instanceof ScriptContentIsEmptyException) {
      throw (ScriptContentIsEmptyException) e;
    }

    //Clear any data that might have been written to the response
    context.getResponse().reset();

    throw new CouldNotExecuteScriptException("Could not execute script", e);
  }
}
