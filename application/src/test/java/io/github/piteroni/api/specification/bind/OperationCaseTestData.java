package io.github.piteroni.api.specification.bind;

import io.github.piteroni.api.specification.model.OperationCase;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class OperationCaseTestData {
  protected SwaggerParseResult parsed;

  protected OpenAPI openAPI;

  protected Operation operation;

  protected OperationCase operationCase;
}
