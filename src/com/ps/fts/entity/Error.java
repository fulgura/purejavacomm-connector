package com.ps.fts.entity;

public class Error {

	private Long id;
	private String rawData;
	private String exceptionMessage;

	public Error(String rawData, String exceptionMessage) {
		super();
		this.rawData = rawData;
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Long getId() {
		return id;
	}

	public String getRawData() {
		return rawData;
	}

}
