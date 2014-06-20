package com.webstub.handler;

public interface RequestHandler<T> {
	void handle(T request);
}
