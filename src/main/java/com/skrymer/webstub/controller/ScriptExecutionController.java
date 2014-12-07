package com.skrymer.webstub.controller;

import com.skrymer.webstub.domain.Script;
import com.skrymer.webstub.scriptexecutor.ScriptExecutor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class ScriptExecutionController {
  private ScriptExecutor scriptExecutor;

  @Autowired
  public ScriptExecutionController(ScriptExecutor scriptExecutor) {
    this.scriptExecutor = scriptExecutor;
  }

  @RequestMapping(value = "/script/execution", method = RequestMethod.POST)
  public void executeScript(HttpServletRequest request, HttpServletResponse response) throws IOException{
    String scriptContent = IOUtils.toString(request.getReader());
    Script script = new Script(1, "ondemand", scriptContent);

    scriptExecutor.execute(script, request, response);
  }
}
