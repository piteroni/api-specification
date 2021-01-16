package io.github.piteroni.api.specification.main.io;

import io.github.piteroni.api.specification.main.io.exception.UnknownPropertyException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static java.lang.String.format;

public class PropertyAccessor {
  private final Properties properties = new Properties();

  public PropertyAccessor(String path) throws IOException {
    properties.load(Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8));
  }

  public String getProperty(final String key) throws UnknownPropertyException {
    String value = properties.getProperty(key);

    if (value == null) {
      throw new UnknownPropertyException(format("unknown property: %s", key));
    }

    return value;
  }
}
