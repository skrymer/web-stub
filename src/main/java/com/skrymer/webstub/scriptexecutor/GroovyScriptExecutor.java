package com.skrymer.webstub.scriptexecutor;

import com.skrymer.webstub.domain.Script;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.skrymer.webstub.util.SyntacticSugar.throwIllegalArgumentExceptionIfNull;

@Component
public class GroovyScriptExecutor implements ScriptExecutor {
  public static final String BINDING_VAR_REQUEST = "request";
  public static final String BINDING_VAR_RESPONSE = "response";
  public static final String BINDING_VAR_APPLICATION = "application";
  public static final String BINDING_VAR_OUT = "out";
  public static final String BINDING_VAR_LOG = "log";

  private static final Log LOG = LogFactory.getLog(GroovyScriptExecutor.class);

  @Override
  public void execute(Script script, HttpServletRequest request, HttpServletResponse response) {
    throwIllegalArgumentExceptionIfNull(script, "script");
    throwIllegalArgumentExceptionIfNull(request, "request");
    throwIllegalArgumentExceptionIfNull(response, "response");

    try {
      Binding bindings = createBindings(script, request, response);
      executeScript(script, bindings);
    } catch (Exception e) {
      LOG.error("Could not execute script", e);

      throw new CouldNotExecuteScriptException("Could not execute script", e);
    }
  }

  private Binding createBindings(Script script, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Binding binding = new Binding();

    binding.setVariable(BINDING_VAR_REQUEST, request);
    binding.setVariable(BINDING_VAR_RESPONSE, response);
    binding.setVariable(BINDING_VAR_APPLICATION, request.getServletContext());
    binding.setVariable(BINDING_VAR_OUT, response.getOutputStream());
    binding.setVariable(BINDING_VAR_LOG, LogFactory.getLog(script.getName()));

    return binding;
  }

  private void executeScript(Script script, Binding bindings) {
    String scriptContent = script.getContent();

    if (scriptContent == null || scriptContent.isEmpty()) {
      throw new ScriptContentIsEmptyException("Script content is empty for script: " + script.getName());
    }

    GroovyShell shell = new GroovyShell(bindings);
    shell.evaluate(script.getContent());
  }
}