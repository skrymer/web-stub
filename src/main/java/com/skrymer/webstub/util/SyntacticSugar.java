package com.skrymer.webstub.util;

import java.util.Collections;
import java.util.List;

public class SyntacticSugar {

  public static <T> List<T> safe(List<T> other) {
    return other == null ? Collections.EMPTY_LIST : other;
  }

  public static void throwIllegalArgumentExceptionIfNull(Object parameter, String parameterName) {
    if (parameter == null) {
      throw new IllegalArgumentException("Parameter " + parameterName + " is not allowed to be null");
    }
  }
}
