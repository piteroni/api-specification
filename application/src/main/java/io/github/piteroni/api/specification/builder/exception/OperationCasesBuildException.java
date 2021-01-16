package io.github.piteroni.api.specification.builder.exception;

import java.util.List;

/**
 * OperationCaseをビルド中に例外が発生したことを表す.
 */
public class OperationCasesBuildException extends Exception {
	private final List<Exception> exceptions;

	private static final long serialVersionUID = 4067896466016320527L;

	public OperationCasesBuildException(List<Exception> exceptions) {
		super();
		this.exceptions = exceptions;
	}

	public List<Exception> getExceptions() {
		return exceptions;
	}
}
