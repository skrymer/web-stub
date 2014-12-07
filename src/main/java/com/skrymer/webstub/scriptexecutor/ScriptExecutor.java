package com.skrymer.webstub.scriptexecutor;

import com.skrymer.webstub.domain.Script;

/**
 * A groovy script executor
 */
public interface ScriptExecutor<T> {

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
   * @param context - the context that the script will have access to
   */
  void execute(Script script, T context);
}
