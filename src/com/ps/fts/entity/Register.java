/**
 * 
 */
package com.ps.fts.entity;

/**
 * @author matilde
 *
 */
public class Register {

	Long id;
	String rawData;
	String formatCode;
	String numberOfLegsEncoded;
	String passengerName;
	String electronicTicketIndicator;
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
	
	
	public Register(String rawData, String formatCode,
			String numberOfLegsEncoded, String passengerName,
			String electronicTicketIndicator, String operatingCarrierPNRCode,
			String fromCityAirportCode, String toCityAirportCode,
			String operatingCarrierDesignator, String fligthNumber,
			String dateOfFligth, String compartmentCode, String seatNumber,
			String checkInSequenceNumber, String passengerStatus) {
		super();
		this.rawData = rawData;
		this.formatCode = formatCode;
		this.numberOfLegsEncoded = numberOfLegsEncoded;
		this.passengerName = passengerName;
		this.electronicTicketIndicator = electronicTicketIndicator;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRawData() {
		return rawData;
	}
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
	public String getFormatCode() {
		return formatCode;
	}
	public void setFormatCode(String formatCode) {
		this.formatCode = formatCode;
	}
	public String getNumberOfLegsEncoded() {
		return numberOfLegsEncoded;
	}
	public void setNumberOfLegsEncoded(String numberOfLegsEncoded) {
		this.numberOfLegsEncoded = numberOfLegsEncoded;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getElectronicTicketIndicator() {
		return electronicTicketIndicator;
	}
	public void setElectronicTicketIndicator(String electronicTicketIndicator) {
		this.electronicTicketIndicator = electronicTicketIndicator;
	}
	public String getOperatingCarrierPNRCode() {
		return operatingCarrierPNRCode;
	}
	public void setOperatingCarrierPNRCode(String operatingCarrierPNRCode) {
		this.operatingCarrierPNRCode = operatingCarrierPNRCode;
	}
	public String getFromCityAirportCode() {
		return fromCityAirportCode;
	}
	public void setFromCityAirportCode(String fromCityAirportCode) {
		this.fromCityAirportCode = fromCityAirportCode;
	}
	public String getToCityAirportCode() {
		return toCityAirportCode;
	}
	public void setToCityAirportCode(String toCityAirportCode) {
		this.toCityAirportCode = toCityAirportCode;
	}
	public String getOperatingCarrierDesignator() {
		return operatingCarrierDesignator;
	}
	public void setOperatingCarrierDesignator(String operatingCarrierDesignator) {
		this.operatingCarrierDesignator = operatingCarrierDesignator;
	}
	public String getFligthNumber() {
		return fligthNumber;
	}
	public void setFligthNumber(String fligthNumber) {
		this.fligthNumber = fligthNumber;
	}
	public String getDateOfFligth() {
		return dateOfFligth;
	}
	public void setDateOfFligth(String dateOfFligth) {
		this.dateOfFligth = dateOfFligth;
	}
	public String getCompartmentCode() {
		return compartmentCode;
	}
	public void setCompartmentCode(String compartmentCode) {
		this.compartmentCode = compartmentCode;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getCheckInSequenceNumber() {
		return checkInSequenceNumber;
	}
	public void setCheckInSequenceNumber(String checkInSequenceNumber) {
		this.checkInSequenceNumber = checkInSequenceNumber;
	}
	public String getPassengerStatus() {
		return passengerStatus;
	}
	public void setPassengerStatus(String passengerStatus) {
		this.passengerStatus = passengerStatus;
	}
	
	

}
