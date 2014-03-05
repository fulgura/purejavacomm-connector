package com.ps.fts.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ps.fts.service.Field;

public class BoardingPass {

	private Long id;
	private String bid;
	private String formatCode;
	private Integer numberOfLegsEncoded;
	private String passengerName;
	private String electronicTicketIndicator;
	private String rawData;
	private List<Item> items = new ArrayList<Item>();

	public BoardingPass(String bid, String formatCode,
			Integer numberOfLegsEncoded, String passengerName,
			String electronicTicketIndicator, String rawData) {
		super();
		this.bid = bid;
		this.formatCode = formatCode;
		this.numberOfLegsEncoded = numberOfLegsEncoded;
		this.passengerName = passengerName;
		this.electronicTicketIndicator = electronicTicketIndicator;
		this.rawData = rawData;
	}

	public BoardingPass(String line, Map<String, Field> headerValues) {
		this(headerValues.get("bid").getValue(), headerValues.get("formatCode")
				.getValue(), headerValues.get("numberOfLegsEncoded")
				.getIntegerValue(), headerValues.get("passengerName")
				.getValue(), headerValues.get("electronicTicketIndicator")
				.getValue(), line);
	}

	public Long getId() {
		return id;
	}

	public String getBid() {
		return bid;
	}

	public String getFormatCode() {
		return formatCode;
	}

	public Integer getNumberOfLegsEncoded() {
		return numberOfLegsEncoded;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public String getElectronicTicketIndicator() {
		return electronicTicketIndicator;
	}

	public String getRawData() {
		return rawData;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
