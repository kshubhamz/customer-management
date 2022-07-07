package com.redhatschool.customermanagement.exception;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = -6434552261010336681L;
	private final String rootCause;

	public NotFoundException(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getRootcause() {
		return rootCause;
	}

}
