package io.github.piteroni.api.specification.model;

import io.github.piteroni.api.specification.data.SearchCondition;

import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;

public class OperationCase implements SearchCondition {
  /**
   * APIのパス.
   */
  private String path;

  /**
   * APIのメソッド.
   */
  private String method;

  /**
   * APIのステータス.
   */
  private Integer statusCode;

  /**
   * APIのリクエストボディ.
   */
  private RequestBody requestBody;

  /**
   * APIのレスポンスオブジェクト.
   */
  private ApiResponse response;

  public OperationCase(String path, String method, Integer statusCode, RequestBody requestBody, ApiResponse response) {
    this.path = path;
    this.method = method;
    this.statusCode = statusCode;
    this.requestBody = requestBody;
    this.response = response;
  }

  /**
   * APIのパスを取得する.
   *
   * @return APIのパス.
   */
  public String getPath() {
    return path;
  }

  /**
   * APIのメソッドの取得する.
   *
   * @return APIのパス.
   */
  public String getMethod() {
    return method;
  }

  /**
   * APIのステータスコード.
   *
   * @return APIのステータスコードを取得する.
   */
  public Integer getStatusCode() {
    return statusCode;
  }

  /**
   * APIのリクエストボディを取得する.
   *
   * @return APIのリクエストボディ.
   */
  public RequestBody getRequestBody() {
    return requestBody;
  }

  /**
   * APIのレスポンスオブジェクトを取得する.
   *
   * @return APIのレスポンスオブジェクト.
   */
  public ApiResponse getResponse() {
    return response;
  }

  /**
   * オブジェクトが一致するか否か取得する.
   *
   * @return object 比較対象オブジェクト.
   * @return オブジェクトが一致するか否か.
   */
  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }

    if (this == object) {
      return true;
    }

    if (object instanceof OperationCase) {
      OperationCase operationCase = (OperationCase) object;

      boolean isRequestBodyEquals = true;
      boolean isResponseEquals = true;

      if (this.requestBody != null) {
        isRequestBodyEquals = this.requestBody.equals(operationCase.getRequestBody());
      }

      if (this.response != null) {
        isResponseEquals = this.response.equals(operationCase.getResponse());
      }

      return this.path.equals(operationCase.getPath())
        && this.method.equals(operationCase.getMethod())
        && this.statusCode.equals(operationCase.getStatusCode())
        && isRequestBodyEquals
        && isResponseEquals;
    } else {
      return false;
    }
  }
}
