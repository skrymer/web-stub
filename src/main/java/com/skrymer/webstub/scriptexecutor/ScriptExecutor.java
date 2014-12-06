package com.skrymer.webstub.scriptexecutor;

import com.skrymer.webstub.domain.Script;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A groovy script executor
 */
public interface ScriptExecutor {

  /**
   * The following variables will be present in the script when it's executed
   * <p/>
   * request - the HttpServletRequest
   * response - the HttpServletResponse
   * application - the ServletContext associated with the servlet
   * out - the PrintWriter associated with the ServletRequest
   * log - the log
   *
   * @param script
   */
  void execute(Script script, HttpServletRequest request, HttpServletResponse response);
}
