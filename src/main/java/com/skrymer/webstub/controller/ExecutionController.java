package com.skrymer.webstub.controller;

import com.skrymer.webstub.handler.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class ExecutionController {
  private RequestHandler requestHandler;

  @Autowired
  public ExecutionController(RequestHandler requestHandler) {
    this.requestHandler = requestHandler;
  }

  @RequestMapping(value = "/execute/**", method = {RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
  public void execute(HttpServletRequest request, HttpServletResponse response) {
    requestHandler.handle(request, response);
  }
}
