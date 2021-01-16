package io.github.piteroni.api.specification.main.io.exception;

import java.util.List;
import java.io.IOException;

public class OpenAPIParseException extends IOException {
  private static final long serialVersionUID = 7170891085623199479L;

  public OpenAPIParseException(List<String> messages) {
    super(String.join(System.getProperty("line.separator"), messages));
  }
}
