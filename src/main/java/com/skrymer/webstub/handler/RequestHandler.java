package com.skrymer.webstub.handler;

public interface RequestHandler<T> {
  void handle(T requestContent);
}
