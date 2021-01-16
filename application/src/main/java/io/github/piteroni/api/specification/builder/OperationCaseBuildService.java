package io.github.piteroni.api.specification.builder;

import io.github.piteroni.api.specification.model.OperationCase;
import io.github.piteroni.api.specification.builder.exception.InvalidSpecification;
import io.github.piteroni.api.specification.builder.exception.OperationCasesBuildException;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class OperationCaseBuildService {
  /**
   * ビルドを行うOperationCaseBuilderオブジェクト.
   */
  private OperationCaseBuilder builder;

  public OperationCaseBuildService(OperationCaseBuilder builder) {
    this.builder = builder;
  }

  /**
   * OpenAPIをビルドし、全てのPathを入力にOperationCaseリストを作成する.
   *
   * @param openAPI ビルド対象のOpenAPIオブジェクト.
   * @return OperationCaseリスト.
   * @throws OperationCasesBuildException OpenAPIに不備がありビルドに失敗した場合に送出される.
   */
  public List<OperationCase> build(OpenAPI openAPI) throws OperationCasesBuildException {
    List<OperationCase> operationCases = new ArrayList<>();
    List<Exception> exceptions = new ArrayList<>();

    boolean isDetectedInvalidSpecification = false;

    // OpenAPI ビルド時にpathsが空のままパースが通ることはないので、ここではgetPaths()の返り値のNullチェックは行わない.
    for (Entry<String, PathItem> pathEntrySet : openAPI.getPaths().entrySet()) {
      try {
        operationCases.addAll(builder.build(pathEntrySet.getKey(), pathEntrySet.getValue()));
      } catch (InvalidSpecification e) {
        isDetectedInvalidSpecification = true;
        exceptions.add(e);
      }
    }

    if (isDetectedInvalidSpecification) {
      throw new OperationCasesBuildException(exceptions);
    }

    return operationCases;
  }
}
