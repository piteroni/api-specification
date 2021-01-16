package io.github.piteroni.api.specification.main.report;

import io.github.piteroni.api.specification.validation.OperationCaseValidationResult;
import io.github.piteroni.api.specification.validation.ValidationResult;

import org.apache.commons.lang3.exception.ExceptionUtils;

import static org.fusesource.jansi.Ansi.*;

import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static java.lang.String.format;

public class VerboseReporter {
  private static final String LINE_SEPARATOR;

  private static final String FAILED_CAPTION;

  private static final String PASSED_CAPTION;

  private static final String FAILED_MARK;

  private static final String PASSED_MARK;

  private Logger logger;

  static {
    LINE_SEPARATOR = System.getProperty("line.separator");
    FAILED_CAPTION = ansi().fgRed().a("FAILED").reset().toString();
    PASSED_CAPTION = ansi().fgGreen().a("PASSED").reset().toString();
    FAILED_MARK = ansi().fgRed().a("✕").reset().toString();
    PASSED_MARK = ansi().fgGreen().a("✓").reset().toString();
  }

	public VerboseReporter(Logger logger) {
    this.logger = logger;
  }

  public void reportValidationResult(Map<String, OperationCaseValidationResult> validationResult) {
    StringBuilder validationResultReport = new StringBuilder();

    validationResultReport.append(LINE_SEPARATOR);

    for (Entry<String, OperationCaseValidationResult> entry : validationResult.entrySet()) {
      StringBuilder content = new StringBuilder();

      String resultCaption = entry.getValue().isFailed() ? FAILED_CAPTION : PASSED_CAPTION;

      content.append(resultCaption + " " + ansi().a(entry.getKey()).reset().toString() + LINE_SEPARATOR);

      entry.getValue().getValidationResultList().forEach(validationCase -> {
        String resultMark = validationCase.isFailed() ? FAILED_MARK : PASSED_MARK;

        content.append(format("  %s %s%s", resultMark, validationCase.getTargetName(), LINE_SEPARATOR));
      });

      validationResultReport.append(content + LINE_SEPARATOR);
    }

    logger.info(validationResultReport.toString());
  }

  public void reportFailure(List<ValidationResult> failedValidations) {
    StringBuilder failureReport = new StringBuilder();

    for (ValidationResult failedValidation : failedValidations) {
      String content = format("● %s: %s", failedValidation.getSummary(), failedValidation.getCause().getMessage());

      failureReport.append(ansi().fgRed().bold().a(content).reset() + LINE_SEPARATOR);
      failureReport.append(LINE_SEPARATOR);
      failureReport.append("  " + ExceptionUtils.getStackTrace(failedValidation.getCause()));
      failureReport.append(LINE_SEPARATOR);
    }

    logger.info(failureReport.toString());
  }
}
