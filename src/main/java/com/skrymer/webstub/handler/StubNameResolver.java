package com.skrymer.webstub.handler;

import javax.servlet.http.HttpServletRequest;

public interface StubNameResolver {

  String resolve(HttpServletRequest request);
}
