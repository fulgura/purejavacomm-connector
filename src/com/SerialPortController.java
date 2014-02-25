package com;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import purejavacomm.CommPortIdentifier;
import purejavacomm.SerialPort;
import purejavacomm.SerialPortEvent;
import purejavacomm.SerialPortEventListener;

public class SerialPortController implements SerialPortEventListener {

	private Enumeration portList;
	private CommPortIdentifier portId;
	private String messageString = "Hello, world!\n";
	private SerialPort serialPort;
	private OutputStream outputStream;
	private InputStream inputStream = null;
	private StringBuffer stringBuffer = new StringBuffer();

	public static void main(String[] args) {

		if(args.length > 0){
			new SerialPortController().initialize(args[0]);
		}else{
			System.out.println("Use:");
			System.out.println("SerialPortController COM[number] (if you are in windows environment)");
			System.out.println("            or");
			System.out.println("SerialPortController /tty[number] (if you are in linux environment)");
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
						serialPort = (SerialPort) portId.open("SerialPortController",
								2000);
						inputStream = serialPort.getInputStream();

						System.out.println("Connected on port: "
								+ portId.getName());

						serialPort.addEventListener(this);
						serialPort.notifyOnDataAvailable(true);
						serialPort.setSerialPortParams(9600,
								SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
								SerialPort.PARITY_EVEN);
						// serialPort.notifyOnCTS(true);
						// serialPort.notifyOnOutputEmpty(true);

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

					// System.out.println("[" + read + "]");

					stringBuffer.append(read);
					if (read.indexOf('\r') != -1) {
						System.out.println("Size:"
								+ stringBuffer.toString().length()
								+ ".Content:" + stringBuffer.toString());
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

	private void processLine(String line) {

		String bid = line.substring(0, 1);
		assert bid == "6";

		String formatCode = line.substring(1, 1 + 1);
		String numberOfLegsEncoded = line.substring(2, 2 + 1);
		String passengerName = line.substring(3, 3 + 20);
		String electronicTicketIndicator = line.substring(23, 23 + 1);
		String operatingCarrierPNRCode = line.substring(24, 24 + 7);
		String fromCityAirportCode = line.substring(31, 31 + 3);
		String toCityAirportCode = line.substring(34, 34 + 3);
		String operatingCarrierDesignator = line.substring(37, 37 + 3);

		System.out.format("%1s,%1s,%20s,%1s,%7s,%3s,%3s,%3s", formatCode,
				numberOfLegsEncoded, passengerName, electronicTicketIndicator,
				operatingCarrierPNRCode, fromCityAirportCode,
				toCityAirportCode, operatingCarrierDesignator);

	}

}
