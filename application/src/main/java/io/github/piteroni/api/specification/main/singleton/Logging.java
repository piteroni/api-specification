package io.github.piteroni.api.specification.main.singleton;

import io.github.piteroni.api.specification.main.io.PropertyAccessor;
import io.github.piteroni.api.specification.main.io.exception.UnknownPropertyException;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
  private Logging() {
  }

  private static Logger reportLogger;

  private static Logger streamLogger = null;

  private static Logger errorStreamLogger = null;

  public static void configuration(String configurePath) throws IOException, UnknownPropertyException {
    PropertyAccessor propertyAccessor = new PropertyAccessor(configurePath);

    Logging.reportLogger = LoggerFactory.getLogger(propertyAccessor.getProperty("reportLoggerName"));
    Logging.streamLogger = LoggerFactory.getLogger(propertyAccessor.getProperty("stdoutLoggerName"));
    Logging.errorStreamLogger = LoggerFactory.getLogger(propertyAccessor.getProperty("stderrLoggerName"));
  }

  public static Logger getReportLogger() {
    return reportLogger;
  }

  public static Logger getStdoutLogger() {
    return streamLogger;
  }

  public static Logger getStderrLogger() {
    return errorStreamLogger;
  }
}
