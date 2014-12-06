package com.skrymer.webstub.scriptexecutor;

public class CouldNotExecuteScriptException extends RuntimeException {

  public CouldNotExecuteScriptException(String message, Exception cause) {
    super(message, cause);
  }
}
