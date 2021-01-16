package io.github.piteroni.api.specification.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "path",
  "method",
  "statusCode",
  "requestData",
  "responseData"
})
public class ApiTestContext {
  /**
   * APIのテスト対象パス.
   */
  @JsonProperty("path")
  private String path;

  /**
   * APIのテスト対象HTTPメソッド.
   */
  @JsonProperty("method")
  private String method;

  /**
   * APIのテスト対象HTTPステータスコード.
   */
  @JsonProperty("statusCode")
  private Integer statusCode;

  /**
   * APIに送信したリクエストデータ.
   */
  @JsonProperty("requestData")
  private RequestData requestData;

  /**
   * APIから送信されたレスポンスデータ.
   */
  @JsonProperty("responseData")
  private ResponseData responseData;

  /**
   * APIのテスト対象パスを取得する.
   *
   * @return APIのテスト対象パス.
   */
  public String getPath() {
    return path;
  }

  /**
   * APIのテスト対象HTTPメソッドを取得できる.
   *
   * @return APIのテスト対象HTTPメソッド.
   */
  public String getMethod() {
    return method;
  }

  /**
   * APIのテスト対象HTTPステータスコードを取得する.
   *
   * @return APIのテスト対象HTTPステータスコード.
   */
  public Integer getStatusCode() {
    return statusCode;
  }

  /**
   * APIに送信したリクエストデータを取得できる.
   *
   * @return APIに送信したリクエストデータ.
   */
  public RequestData getRequestData() {
    return requestData;
  }

  /**
   * APIから送信されたレスポンスデータを取得できる.
   *
   * @return APIから送信されたレスポンスデータ.
   */
  public ResponseData getResponseData() {
    return responseData;
  }

  /**
   * オブジェクトが一致するか否か取得する.
   *
   * @return object 比較対象オブジェクト.
   * @return オブジェクトが一致するか否か.
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (object instanceof ApiTestContext) {
      ApiTestContext context = (ApiTestContext) object;

      return this.path.equals(context.getPath())
        && this.method.equals(context.getMethod())
        && this.statusCode.equals(context.getStatusCode())
        && this.requestData.getAdditionalProperties().equals(context.getRequestData().getAdditionalProperties())
        && this.responseData.getAdditionalProperties().equals(context.getResponseData().getAdditionalProperties());
    } else {
      return false;
    }
  }
}
