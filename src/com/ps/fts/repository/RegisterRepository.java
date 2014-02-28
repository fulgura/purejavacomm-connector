package com.ps.fts.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.ps.fts.entity.Register;

public class RegisterRepository {

	private Connection connection;

	public RegisterRepository(Connection conn) {
		this.connection = conn;
	}

	public void save(Register register) throws SQLException {
		Statement statement = this.connection.createStatement();

		String sql = "INSERT INTO register( ID,"
				+ "check_in_sequence_number, compartment_code, date_of_fligth, "
				+ "electronic_ticket_indicator, fligth_number, format_code, from_city_airport_code, "
				+ "number_of_legs_encoded, operating_carrier_designator, operating_carrierpnrcode, "
				+ "passenger_name, passenger_status, seat_number, to_city_airport_code, raw_data)"
				+ " VALUES ( nextval('hibernate_sequence'), '"
				+ register.getCheckInSequenceNumber()
				+ "', '"
				+ register.getCompartmentCode()
				+ "', '"
				+ register.getDateOfFligth()
				+ "', '"
				+ register.getElectronicTicketIndicator()
				+ "', '"
				+ register.getFligthNumber()
				+ "', '"
				+ register.getFormatCode()
				+ "', '"
				+ register.getFromCityAirportCode()
				+ "', '"
				+ register.getNumberOfLegsEncoded()
				+ "', '"
				+ register.getOperatingCarrierDesignator()
				+ "', '"
				+ register.getOperatingCarrierPNRCode()
				+ "', '"
				+ register.getPassengerName()
				+ "', '"
				+ register.getPassengerStatus()
				+ "','"
				+ register.getSeatNumber()
				+ "', '"
				+ register.getToCityAirportCode()
				+ "', '"
				+ register.getRawData() + "');";

		System.out.println(sql);
		statement.executeUpdate(sql);

		statement.close();
	}
}
