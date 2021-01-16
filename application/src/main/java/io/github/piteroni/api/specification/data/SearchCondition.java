package io.github.piteroni.api.specification.data;

/**
 * ApiTestContextの検索の際に使用される検索条件オブジェクトを表す.
 */
public interface SearchCondition {
  /**
   * APIのパスを取得する.
   *
   * @return APIのパス.
   */
  public String getPath();

  /**
   * APIのHTTPメソッドを取得する.
   *
   * @return APIのHTTPメソッド.
   */
  public String getMethod();

  /**
   * APIレスポンスのステータスコードを取得する.
   *
   * @return APIレスポンスのステータスコード.
   */
  public Integer getStatusCode();
}
