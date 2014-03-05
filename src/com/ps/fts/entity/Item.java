/**
 * 
 */
package com.ps.fts.entity;

import java.util.Map;

import com.ps.fts.service.Field;

/**
 * @author matilde
 * 
 */
public class Item {

	Long id;
	String operatingCarrierPNRCode;
	String fromCityAirportCode;
	String toCityAirportCode;
	String operatingCarrierDesignator;
	String fligthNumber;
	String dateOfFligth;

	String compartmentCode;
	String seatNumber;
	String checkInSequenceNumber;
	String passengerStatus;

	public Item(String operatingCarrierPNRCode, String fromCityAirportCode,
			String toCityAirportCode, String operatingCarrierDesignator,
			String fligthNumber, String dateOfFligth, String compartmentCode,
			String seatNumber, String checkInSequenceNumber,
			String passengerStatus) {
		super();
		this.operatingCarrierPNRCode = operatingCarrierPNRCode;
		this.fromCityAirportCode = fromCityAirportCode;
		this.toCityAirportCode = toCityAirportCode;
		this.operatingCarrierDesignator = operatingCarrierDesignator;
		this.fligthNumber = fligthNumber;
		this.dateOfFligth = dateOfFligth;
		this.compartmentCode = compartmentCode;
		this.seatNumber = seatNumber;
		this.checkInSequenceNumber = checkInSequenceNumber;
		this.passengerStatus = passengerStatus;
	}

	public Item(Map<String, Field> mandatoryValues) {
		this(mandatoryValues.get("operatingCarrierPNRCode").getValue(),
				mandatoryValues.get("fromCityAirportCode").getValue(),
				mandatoryValues.get("toCityAirportCode").getValue(),
				mandatoryValues.get("operatingCarrierDesignator").getValue(),
				mandatoryValues.get("fligthNumber").getValue(), mandatoryValues
						.get("dateOfFligth").getValue(), mandatoryValues.get(
						"compartmentCode").getValue(), mandatoryValues.get(
						"seatNumber").getValue(), mandatoryValues.get(
						"checkInSequenceNumber").getValue(), mandatoryValues
						.get("passengerStatus").getValue());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperatingCarrierPNRCode() {
		return operatingCarrierPNRCode;
	}

	public String getFromCityAirportCode() {
		return fromCityAirportCode;
	}

	public String getToCityAirportCode() {
		return toCityAirportCode;
	}

	public String getOperatingCarrierDesignator() {
		return operatingCarrierDesignator;
	}

	public String getFligthNumber() {
		return fligthNumber;
	}

	public String getDateOfFligth() {
		return dateOfFligth;
	}

	public String getCompartmentCode() {
		return compartmentCode;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public String getCheckInSequenceNumber() {
		return checkInSequenceNumber;
	}

	public String getPassengerStatus() {
		return passengerStatus;
	}
}
