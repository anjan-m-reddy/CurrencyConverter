package com.anz.fx.dto;

public class FxMatrixDto {

	private String currencyName;
	private String currencyRef;

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyRef() {
		return currencyRef;
	}

	public void setCurrencyRef(String currencyRef) {
		this.currencyRef = currencyRef;
	}

	@Override
	public String toString() {
		return "FxMatrixDto [currencyName=" + currencyName + ", currencyRef=" + currencyRef + "]";
	}
	
}
