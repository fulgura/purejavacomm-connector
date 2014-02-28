package com.ps.fts;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import purejavacomm.CommPortIdentifier;
import purejavacomm.SerialPort;
import purejavacomm.SerialPortEvent;
import purejavacomm.SerialPortEventListener;

import com.ps.fts.entity.Register;
import com.ps.fts.repository.RegisterRepository;

public class SerialPortScanner implements SerialPortEventListener {

	private Enumeration portList;
	private CommPortIdentifier portId;
	private SerialPort serialPort;
	private InputStream inputStream = null;
	private StringBuffer stringBuffer = new StringBuffer();
	private RegisterRepository registerRepository;

	public SerialPortScanner(String url) throws SQLException {
		this.registerRepository = new RegisterRepository(
				DriverManager.getConnection(url));
	}

	public static void main(String[] args) {

		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;

		}

		if (args.length > 0) {
			try {
				new SerialPortScanner(
						"jdbc:postgresql://localhost/DGAC?user=admin&password=admin")
						.initialize(args[0]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		} else {
			System.out.println("Use:");
			System.out
					.println("SerialPortController COM[number] (if you are in windows environment)");
			System.out.println("            or");
			System.out
					.println("SerialPortController /tty[number] (if you are in linux environment)");
		}

	}

	private void initialize(String portName) {
		portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			System.out.println(portId);

			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portId.getName().equals(portName)) {
					try {
						serialPort = (SerialPort) portId.open(
								"SerialPortController", 2000);
						inputStream = serialPort.getInputStream();

						System.out.println("Connected on port: "
								+ portId.getName());

						serialPort.addEventListener(this);
						serialPort.notifyOnDataAvailable(true);
						serialPort.setSerialPortParams(9600,
								SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
								SerialPort.PARITY_EVEN);

						while (true) {
							try {
								Thread.sleep(100);
							} catch (Exception ex) {
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void serialEvent(SerialPortEvent event) {

		try {

			switch (event.getEventType()) {
			case SerialPortEvent.DATA_AVAILABLE:

				byte[] buffer = new byte[inputStream.available()];
				int n = inputStream.read(buffer);
				String read = new String(buffer);// .trim();

				if (!read.isEmpty()) {

					stringBuffer.append(read);
					if (read.indexOf('\r') != -1) {
						System.out.println("Size:"
								+ stringBuffer.toString().length()
								+ ".Content:"
								+ stringBuffer.toString().replace('\r', ' '));
						this.processLine(stringBuffer.toString().trim());
						stringBuffer = new StringBuffer();
					}
				}

				break;

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void splitLine(String line, int numberOfLegsEncoded){
		
		List<String> spittedLines = new ArrayList<String>();
		
		int mandatoryItemsInitialIndex = 1;
		int mandatoryItemsFinalIndex = mandatoryItemsInitialIndex + 60;

		int conditionalItemsPartInitialIndex = mandatoryItemsFinalIndex;
		int conditionalItemsFinalIndex = conditionalItemsPartInitialIndex;
		
		for (int i = 0; i < numberOfLegsEncoded; i++) {
			String mandatoryItemsLine = line.substring(mandatoryItemsInitialIndex, mandatoryItemsFinalIndex);
			spittedLines.add(mandatoryItemsLine);
			
			int conditionalItemsSize = Integer.parseInt(
					line.substring(mandatoryItemsFinalIndex - 2 , mandatoryItemsFinalIndex), 16);
			
			conditionalItemsFinalIndex = conditionalItemsPartInitialIndex + conditionalItemsSize;
			String conditionalItemsLine = line.substring(conditionalItemsPartInitialIndex, conditionalItemsFinalIndex);
			spittedLines.add(conditionalItemsLine);
			
		}
	}
	
	private void processLine(String line) throws SQLException {

		String bid = line.substring(0, 1);
		assert bid == "6";

		// MANDATORY ITEMS
		String formatCode = line.substring(1, 1 + 1);
		String numberOfLegsEncoded = line.substring(2, 2 + 1);
		
		this.splitLine(line, Integer.parseInt(numberOfLegsEncoded));
		
		String passengerName = line.substring(3, 3 + 20);
		String electronicTicketIndicator = line.substring(23, 23 + 1);
		String operatingCarrierPNRCode = line.substring(24, 24 + 7);
		String fromCityAirportCode = line.substring(31, 31 + 3);
		String toCityAirportCode = line.substring(34, 34 + 3);
		String operatingCarrierDesignator = line.substring(37, 37 + 3);
		String fligthNumber = line.substring(40, 40 + 5);
		String dateOfFligth = line.substring(45, 45 + 3);

		String compartmentCode = line.substring(48, 48 + 1);
		String seatNumber = line.substring(49, 49 + 4);
		String checkInSequenceNumber = line.substring(53, 53 + 5);
		String passengerStatus = line.substring(58, 58 + 1);

		registerRepository.save(new Register(line.trim(), formatCode,
				numberOfLegsEncoded, passengerName, electronicTicketIndicator,
				operatingCarrierPNRCode, fromCityAirportCode,
				toCityAirportCode, operatingCarrierDesignator, fligthNumber,
				dateOfFligth, compartmentCode, seatNumber,
				checkInSequenceNumber, passengerStatus));
		
		int fieldSizeOfFollowingVariableSizeField = Integer.parseInt(
				line.substring(59, 59 + 2), 16);

		System.out.println("Mandatory Fields");
		System.out.format(
				"%1s,%1s,%20s,%1s,%7s,%3s,%3s,%3s,%5s,%3s,%1s,%4s,%5s,%1s,%2s",
				formatCode, numberOfLegsEncoded, passengerName,
				electronicTicketIndicator, operatingCarrierPNRCode,
				fromCityAirportCode, toCityAirportCode,
				operatingCarrierDesignator, fligthNumber, dateOfFligth,
				compartmentCode, seatNumber, checkInSequenceNumber,
				passengerStatus, fieldSizeOfFollowingVariableSizeField);

		System.out.println();
		System.out.println("Conditional Items");

		if (fieldSizeOfFollowingVariableSizeField > 0) {

			String conditionalItemsLine = line.substring(61,
					61 + fieldSizeOfFollowingVariableSizeField);

			String beginningOfVersionNumber = this.safeSubstring(
					conditionalItemsLine, 0, 1);
			String versionNumber = this.safeSubstring(conditionalItemsLine, 1,
					1);
			String fieldSizeOfFollowingStructuredMessage = this.safeSubstring(
					conditionalItemsLine, 2, 2);
			String passengerDescription = this.safeSubstring(
					conditionalItemsLine, 2, 1);
			String sourceOfCheckIn = this.safeSubstring(conditionalItemsLine,
					3, 1);
			String sourceOfBoardingPassIssuance = this.safeSubstring(
					conditionalItemsLine, 4, 1);
			String dateOfIssueOfBoardingPass = this.safeSubstring(
					conditionalItemsLine, 5, 4);
			String documentType = this
					.safeSubstring(conditionalItemsLine, 9, 1);
			String airlineDesignatorOfBoardingPassIssuer = this.safeSubstring(
					conditionalItemsLine, 10, 3);
			String baggageTagLicencePlateNumer = this.safeSubstring(
					conditionalItemsLine, 13, 13);
			String fieldSizeOfFollowingNextStructuredMessage = this
					.safeSubstring(conditionalItemsLine, 26, 2);
			String airlineNumericCode = this.safeSubstring(
					conditionalItemsLine, 28, 3);
			String documentFormSerialNumber = this.safeSubstring(
					conditionalItemsLine, 31, 10);
			String selecteeIndicator = this.safeSubstring(conditionalItemsLine,
					41, 1);
			String internationalDocumentationVerification = this.safeSubstring(
					conditionalItemsLine, 42, 1);
			String marketingCarrierDesignator = this.safeSubstring(
					conditionalItemsLine, 43, 3);
			String frequentFlyerAirlineDesignator = this.safeSubstring(
					conditionalItemsLine, 46, 3);
			String frequentFlyerNumber = this.safeSubstring(
					conditionalItemsLine, 49, 16);
			String idAdIndicator = this.safeSubstring(conditionalItemsLine,
					49 + 16, 1);
			String freeBaggageAllowence = this.safeSubstring(
					conditionalItemsLine, 49 + 16 + 1, 3);

			System.out
					.format("%1s,%1s,%2s,%1s,%1s,%1s,%4s,%1s,%3s,%13s,%2s,%3s,%10s,%1s,%1s,%3s,%3s,%16s,%1s,%3s",
							beginningOfVersionNumber, versionNumber,
							fieldSizeOfFollowingStructuredMessage,
							passengerDescription, sourceOfCheckIn,
							sourceOfBoardingPassIssuance,
							dateOfIssueOfBoardingPass, documentType,
							airlineDesignatorOfBoardingPassIssuer,
							baggageTagLicencePlateNumer,
							fieldSizeOfFollowingNextStructuredMessage,
							airlineNumericCode, documentFormSerialNumber,
							selecteeIndicator,
							internationalDocumentationVerification,
							marketingCarrierDesignator,
							frequentFlyerAirlineDesignator,
							frequentFlyerNumber, idAdIndicator,
							freeBaggageAllowence);
		}
	}

	private String safeSubstring(String line, int beginIndex, int amount) {

		String substring = "";
		if (line.length() >= (beginIndex + amount)) {
			substring = line.substring(beginIndex, beginIndex + amount);
		}

		return substring;
	}
}
