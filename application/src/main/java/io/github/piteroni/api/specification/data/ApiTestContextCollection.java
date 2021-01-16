package io.github.piteroni.api.specification.data;

import io.github.piteroni.api.specification.data.exception.ApiTestContextDoesNotExistException;

import java.util.List;
import java.util.LinkedList;

public class ApiTestContextCollection {
  /**
   * ApiTestContextを保持する配列.
   */
  private List<ApiTestContext> contexts;

  public ApiTestContextCollection(List<ApiTestContext> contexts) {
    this.contexts = contexts;
  }

  /**
   * 指定された条件に一致するApiTestContextをリストから取り出す.
   *
   * @param condition 検索条件を表すオブジェクト.
   * @return 指定された条件に一致するApiTestContext.
   * @throws ApiTestContextDoesNotExistException 検索結果が0件の場合に送出される.
   */
  public List<ApiTestContext> take(SearchCondition condition) throws ApiTestContextDoesNotExistException {
    List<ApiTestContext> result = new LinkedList<>();
    List<ApiTestContext> removeItem = new LinkedList<>();

    contexts.forEach(context -> {
      boolean isEquals = condition.getPath().equals(context.getPath())
          && condition.getMethod().equals(context.getMethod())
          && condition.getStatusCode().equals(context.getStatusCode());

      if (isEquals) {
        result.add(context);
        removeItem.add(context);
      }
    });

    contexts.removeAll(removeItem);

    if (result.isEmpty()) {
      throw new ApiTestContextDoesNotExistException("The test target does not exist.");
    }

    return result;
  }

  /**
   * 保持しているApiTestContextリストを取得する.
   *
   * @return 保持しているApiTestContextリスト.
   */
  public List<ApiTestContext> getContexts() {
    return contexts;
  }

  /**
   * 保持しているApiTestContextリストが空か否かを取得する.
   *
   * @return 保持しているApiTestContextリストが空か否か.
   */
  public boolean isEmpty() {
    return contexts.isEmpty();
  }
}
