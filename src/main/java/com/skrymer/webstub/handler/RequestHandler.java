package com.skrymer.webstub.handler;

public interface RequestHandler<T, V> {
  void handle(T request, V response);
}
