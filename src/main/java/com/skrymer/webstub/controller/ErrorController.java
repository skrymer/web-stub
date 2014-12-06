package com.skrymer.webstub.controller;

import com.skrymer.webstub.scriptexecutor.CouldNotExecuteScriptException;
import com.skrymer.webstub.service.StubNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorController {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(StubNotFoundException.class)
  @ResponseBody
  public ErrorInfo handleStubNotFoundException(HttpServletRequest req, Exception ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(CouldNotExecuteScriptException.class)
  @ResponseBody
  public ErrorInfo handleCouldNotExecuteScriptException(HttpServletRequest req, Exception ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ErrorInfo handleException(HttpServletRequest req, Exception ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  private static class ErrorInfo {
    public final String url;
    public final String errorMessage;

    public ErrorInfo(String url, Exception ex) {
      this.url = url;
      this.errorMessage = ex.getLocalizedMessage();
    }
  }
}
