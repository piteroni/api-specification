package io.github.piteroni.api.specification.validation;

import java.util.List;
import java.util.ArrayList;

public class OperationCaseValidationResult {
  /**
   * 検証対象のOperationCase名.
   */
  private String operationCaseName;

  /**
   * 1つ以上の要素の検証に失敗したかを表す.
   */
  private boolean isFailed = false;

  /**
   * 検証結果を格納するリスト.
   */
  private List<ValidationResult> validationResultList = new ArrayList<>();

  public OperationCaseValidationResult(String operationCaseName) {
    this.operationCaseName = operationCaseName;
  }

  /**
   * 検証に失敗したかを取得する.
   *
   * @return 検証に失敗したか.
   */
  public boolean isFailed() {
    return isFailed;
  }

  /**
   * 検証に失敗したことを記録する.
   */
  public void fail() {
    if (!isFailed) {
      isFailed = true;
    }
  }

  /**
   * 検証対象のOperationCase名を取得する.
   *
   * @return 検証対象のoperationCase名.
   */
  public String getOperationCaseName() {
    return operationCaseName;
  }

  /**
   * 検証結果リストを取得する.
   *
   * @return 検証結果リスト.
   */
  public List<ValidationResult> getValidationResultList() {
    return validationResultList;
  }

  /**
   * 検証結果をリストに追加する.
   *
   * @param validationResult 検証結果.
   */
  public OperationCaseValidationResult addValidationResult(ValidationResult validationResult) {
    validationResultList.add(validationResult);

    return this;
  }
}
