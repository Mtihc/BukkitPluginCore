package com.mtihc.minecraft.core1.exceptions;

public class ArgumentFormatException extends Exception {

	private static final long serialVersionUID = -8218085905980520989L;

	public ArgumentFormatException(String message) {
		super(message);
	}

	public ArgumentFormatException(String message, Throwable cause) {
		super(message, cause);
	}

}
