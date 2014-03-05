package com.ps.fts.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.ps.fts.entity.BoardingPass;
import com.ps.fts.entity.Error;
import com.ps.fts.entity.Item;

public class RegisterRepository {

	private Connection connection;

	public RegisterRepository(Connection conn) {
		this.connection = conn;
	}

	public void save(Item register) throws SQLException {
		Statement statement = this.connection.createStatement();

		String sql = "INSERT INTO register( ID,"
				+ "check_in_sequence_number, compartment_code, date_of_fligth, "
				+ " fligth_number, from_city_airport_code, "
				+ "operating_carrier_designator, operating_carrierpnrcode, "
				+ " passenger_status, seat_number, to_city_airport_code)"
				+ " VALUES ( nextval('hibernate_sequence'), '"
				+ register.getCheckInSequenceNumber() + "', '"
				+ register.getCompartmentCode() + "', '"
				+ register.getDateOfFligth() + "', '"
				+ register.getFligthNumber() + "', '"
				+ register.getFromCityAirportCode() + "', '"
				+ register.getOperatingCarrierDesignator() + "', '"
				+ register.getOperatingCarrierPNRCode() + "', '"
				+ register.getPassengerStatus() + "','"
				+ register.getSeatNumber() + "', '"
				+ register.getToCityAirportCode() + "');";

		System.out.println(sql);
		statement.executeUpdate(sql);

		statement.close();
	}

	public void save(BoardingPass boardingPass) {
		
		
	}

	public void save(Error error) {
		
		
	}
}
