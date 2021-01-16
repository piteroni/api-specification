package io.github.piteroni.api.specification.validation;

public class ValidationResult {
  /**
   * 検証対象の論理名.
   */
  private String targetName;

  /**
   * 検証対象の概要.
   */
  private String summary;

  /**
   * 検証の際に発生した例外.
   */
  private Throwable cause;

  /**
   * 検証に失敗したかを表す.
   */
  private boolean isFailed = false;

  public ValidationResult(String targetName, String summary) {
    this.targetName = targetName;
    this.summary = summary;
  }

  /**
   * 検証対象の論理名を取得する.
   *
   * @return 検証対象の論理名.
   */
  public String getTargetName() {
    return targetName;
  }

  /**
   * 検証対象の概要を取得する.
   *
   * @return 検証対象の概要.
   */
  public String getSummary() {
    return summary;
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
   * 検証の際に発生した例外を取得する.
   *
   * @return 検証の際に発生した例外.
   */
  public Throwable getCause() {
    return cause;
  }

  /**
   * 検証の際に発生した例外を設定する.
   *
   * @param cause 検証の際に発生した例外.
   */
  public void setCause(Throwable cause) {
    this.cause = cause;
  }
}
