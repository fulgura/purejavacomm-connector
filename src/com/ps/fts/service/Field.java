package com.ps.fts.service;

public class Field {
	private int amountCharacters;
	private String fieldName;
	private String value;

	public Field(int amountCharacters, String fieldName) {
		super();
		this.amountCharacters = amountCharacters;
		this.fieldName = fieldName;
	}

	public Field(int amountCharacters, String fieldName, String value) {
		super();
		this.amountCharacters = amountCharacters;
		this.fieldName = fieldName;
		this.value = value;
	}

	public Integer getIntegerValue() {
		return Integer.parseInt(value);
	}

	public Integer getHexaValue() {
		return Integer.parseInt(value, 16);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getAmountCharacters() {
		return amountCharacters;
	}

	public String getFieldName() {
		return fieldName;
	}

	@Override
	public String toString() {
		return "FieldValue [amountCharacters=" + amountCharacters
				+ ", fieldName=" + fieldName + ", value=" + value + "]";
	}

}