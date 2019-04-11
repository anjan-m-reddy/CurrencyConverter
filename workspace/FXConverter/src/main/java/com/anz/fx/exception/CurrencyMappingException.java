package com.anz.fx.exception;

public class CurrencyMappingException extends Exception {
	
	private static final long serialVersionUID = 5043580362519346867L;
	private String message;
	
	public CurrencyMappingException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "CurrenctMappingException [message=" + message + "]";
	}

}
