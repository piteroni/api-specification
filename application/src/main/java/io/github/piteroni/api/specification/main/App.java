package io.github.piteroni.api.specification.main;

import io.github.piteroni.api.specification.model.OperationCase;
import io.github.piteroni.api.specification.data.ApiTestContextCollection;
import io.github.piteroni.api.specification.builder.OperationCaseBuilder;
import io.github.piteroni.api.specification.builder.OperationCaseBuildService;
import io.github.piteroni.api.specification.builder.exception.OperationCasesBuildException;
import io.github.piteroni.api.specification.validation.SpecificationValidator;
import io.github.piteroni.api.specification.main.io.ResourcesLoader;
import io.github.piteroni.api.specification.main.io.exception.OpenAPIParseException;
import io.github.piteroni.api.specification.main.io.exception.UnknownPropertyException;
import io.github.piteroni.api.specification.main.report.VerboseReporter;
import io.github.piteroni.api.specification.main.singleton.Logging;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.ResolverCache;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

public class App {
  private static final int OK = 0;

  private static final int EXIT_EXCEPTION = 1;

  private static final int EXIT_ERROR = -1;

  private static final String LOGGING_PROPERTIES = "/application/src/main/resources/logback.properties";

  private static boolean isExceptionThrown = false;

  static {
    try {
      Logging.configuration(LOGGING_PROPERTIES);
    } catch (IOException | UnknownPropertyException e) {
      isExceptionThrown = true;
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    if (isExceptionThrown) {
      System.exit(EXIT_ERROR);
    }

    App app = new App();

    Integer returnCode = app.run(args);

    System.exit(returnCode);
  }

  private int run(String[] args) {
    Options options = new Options();

    options.addOption(Option.builder("h")
      .longOpt("help")
      .desc("print this message")
      .build());

    options.addOption(Option.builder()
        .longOpt("oas")
        .hasArg()
        .argName("oas-path")
        .desc("Path to OpenAPI Specification")
        .build());

    options.addOption(Option.builder()
        .longOpt("data")
        .hasArg()
        .argName("api-context-path")
        .desc("Path to Api Context")
      .build());

    CommandLine line = null;

    try {
      line = new DefaultParser().parse(options, args);
    } catch (ParseException e) {
      e.printStackTrace();

      return EXIT_ERROR;
    }

    if (line.hasOption("h")) {
      new HelpFormatter().printHelp("api-specification-validation", options);

      return OK;
    }

    OpenAPI openAPI = null;
    ApiTestContextCollection apiTestContexts = null;

    try {
      openAPI = ResourcesLoader.getOpenAPI(line.getOptionValue("oas"));
    } catch (OpenAPIParseException e) {
      e.printStackTrace();

      return EXIT_ERROR;
    }

    try {
      apiTestContexts = new ApiTestContextCollection(
          ResourcesLoader.getApiTestContexts(line.getOptionValue("data")));
    } catch (IOException e) {
      e.printStackTrace();

      return EXIT_ERROR;
    }

    OperationCaseBuildService buildService = new OperationCaseBuildService(
        new OperationCaseBuilder(new ResolverCache(openAPI, null, null)));

    List<OperationCase> operationCases = null;

    try {
      operationCases = buildService.build(openAPI);
    } catch (OperationCasesBuildException e) {
      Logging.getStderrLogger().error("Failed build operationCases");

      e.getExceptions().forEach(exception -> {
        Logging.getStderrLogger().error(ExceptionUtils.getStackTrace(exception));
      });

      return EXIT_EXCEPTION;
    }

    Logging.getStdoutLogger().info("Starts to validate the apiTest context");

    SpecificationValidator validator = new SpecificationValidator(operationCases);

    validator.validate(apiTestContexts);

    VerboseReporter reporter = new VerboseReporter(Logging.getReportLogger());

    reporter.reportValidationResult(validator.getValidateResultMap());
    reporter.reportFailure(validator.getFailedValidations());

    if (!apiTestContexts.isEmpty()) {
      Logging.getStderrLogger().error("There are untested apiTest contexts: {}", new JSONArray(apiTestContexts.getContexts()));
    }

    if (validator.isFailed() || !apiTestContexts.isEmpty()) {
      Logging.getStderrLogger().error("Invalid apiTest context found");

      return EXIT_EXCEPTION;
    } else {
      Logging.getStdoutLogger().info("ApiTest contexts has been verify");

      return OK;
    }
  }
}
