package com.mtihc.minecraft.core1.exceptions;

public class ArgumentIndexException extends Exception {

	private static final long serialVersionUID = 3019035199957369267L;

	public ArgumentIndexException(String expected, int atIndex, Throwable cause) {
		super("Argument not found. Expected " + expected + " at position "
				+ atIndex + ".", cause);
	}

	public ArgumentIndexException(String message) {
		super(message);
	}

	public ArgumentIndexException(String message, Throwable cause) {
		super(message, cause);
	}

}
