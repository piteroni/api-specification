package io.github.piteroni.api.specification.builder.exception;

/**
 * コンポーネント参照に問題がある場合を表す.
 */
public class ReferenceResolveException extends InvalidSpecification {
  private static final long serialVersionUID = 3000559210203057836L;

  public ReferenceResolveException(String message) {
    super(message);
  }
}
