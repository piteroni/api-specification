package io.github.piteroni.api.specification.data.exception;

/**
 * ApiTestContextの検索結果が0件の場合を表す.
 */
public class ApiTestContextDoesNotExistException extends Exception {
	private static final long serialVersionUID = -6285230971024699809L;

	public ApiTestContextDoesNotExistException(String message) {
		super(message);
	}
}
