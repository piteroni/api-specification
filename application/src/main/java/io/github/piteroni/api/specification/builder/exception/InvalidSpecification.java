package io.github.piteroni.api.specification.builder.exception;

/**
 * 不正な仕様が検出された場合を表す.
 */
public class InvalidSpecification extends Exception {
	private static final long serialVersionUID = -3094936594002913638L;

	public InvalidSpecification(String message) {
		super(message);
	}
}
