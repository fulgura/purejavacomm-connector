package com.ps.fts.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ps.fts.entity.Register;

public class Resolution792Parser {

	public static void main(String[] args) {
//		new Resolution792Parser()
//				.parse("6M1TS AEP00XX448/UNO    RECLOC UIOGYEXX 9999 050Y001A0001 100");

		new Resolution792Parser()
		 .parse("6M1CAPONE/DINO         YLE8J9M IAHPHLCO 1676 349G32A 12   13A>30B        CO 29             00   US 6655900             ADk+5kMZqFDwRftgGZvhWMnNH0IrAxugr7e1h5cW+SNETsM0oCY2uwRG");

//		new Resolution792Parser()
//				.parse("6M1HERRERA/BEETHOVEN   E5P3HRC UIOBOGAV 7372 349Y024C0069 348>2180      B1A              29             0    AV 13428027712");
	}

	private List<Register> splitLine(String line, int numberOfLegsEncoded) {

		List<Register> registers = new ArrayList<Register>();

		int mandatoryItemsInitialIndex = 0;
		int mandatoryItemsFinalIndex = mandatoryItemsInitialIndex + 35;

		int conditionalItemsPartInitialIndex = mandatoryItemsFinalIndex + 2;
		int conditionalItemsFinalIndex = conditionalItemsPartInitialIndex;

		for (int i = 0; i < numberOfLegsEncoded; i++) {

			String mandatoryItemsLine = line.substring(
					mandatoryItemsInitialIndex, mandatoryItemsFinalIndex);

			int conditionalItemsSize = Integer
					.parseInt(line.substring(mandatoryItemsFinalIndex ,
							mandatoryItemsFinalIndex + 2), 16);

			conditionalItemsFinalIndex = conditionalItemsPartInitialIndex
					+ conditionalItemsSize;

			String conditionalItemsLine = line.substring(
					conditionalItemsPartInitialIndex,
					conditionalItemsFinalIndex);

			registers.add(this.processLine(mandatoryItemsLine,
					conditionalItemsLine));
		}

		return registers;
	}

	private Register processLine(String mandatoryItemsLine,
			String conditionalItemsLine) {

		Map<String, Field> mandatoryValues = this.retrieveValues(
				mandatoryItemsLine, new Field[] {
						new Field(7, "operatingCarrierPNRCode"),
						new Field(3, "fromCityAirportCode"),
						new Field(3, "toCityAirportCode"),
						new Field(3, "operatingCarrierDesignator"),
						new Field(5, "fligthNumber"),
						new Field(3, "dateOfFligth"),
						new Field(1, "compartmentCode"),
						new Field(4, "seatNumber"),
						new Field(5, "checkInSequenceNumber"),
						new Field(1, "passengerStatus") });

//		if (conditionalItemsLine != null && !conditionalItemsLine.isEmpty()) {
//			Map<String, Field> headerConditionalValues = this.retrieveValues(
//					conditionalItemsLine, new Field[] {
//							new Field(1, "beginningOfVersionNumber"),
//							new Field(1, "versionNumber"),
//							new Field(2,
//									"fieldSizeOfFollowingStructuredMessage") });
//
//			Integer size = headerConditionalValues.get(
//					"fieldSizeOfFollowingStructuredMessage").getHexaValue();
//
//			Map<String, Field> conditionalValues = this.retrieveValues(
//					conditionalItemsLine.substring(4, 4 + size), new Field[] {
//							new Field(1, "passengerDescription"),
//							new Field(1, "sourceOfCheckIn"),
//							new Field(1, "sourceOfBoardingPassIssuance"),
//							new Field(4, "dateOfIssueOfBoardingPass"),
//							new Field(1, "documentType"),
//							new Field(3,
//									"airlineDesignatorOfBoardingPassIssuer"),
//							new Field(13, "baggageTagLicencePlateNumer") });
//
//			String nextSection = conditionalItemsLine
//					.substring(
//							6 + size,
//							+6
//									+ size
//									+ this.retrieveValue(conditionalItemsLine,
//											4 + size, 4 + size + 2,
//											"fieldSizeOfFollowingNextStructuredMessage")
//											.getHexaValue());
//
//		}

		return new Register(mandatoryValues);
	}

	private Field retrieveValue(String line, int from, int to, String fieldName) {
		String value = line.substring(from, to);
		return new Field(to - from, fieldName, value);
	}

	private Map<String, Field> retrieveValues(String line, Field[] fields) {

		Map<String, Field> values = new HashMap<String, Field>();
		int pos = 0;
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setValue(line.substring(pos,
					pos + field.getAmountCharacters()));
			values.put(field.getFieldName(), field);
			pos += fields[i].getAmountCharacters();
		}

		return values;
	}

	public void parse(String line) {

		Map<String, Field> headerValues = this.retrieveValues(line,
				new Field[] { new Field(1, "bid"), new Field(1, "formatCode"),
						new Field(1, "numberOfLegsEncoded"),
						new Field(20, "passengerName"),
						new Field(1, "electronicTicketIndicator") });

		List<Register> registers = this.splitLine(
				line.substring(24, line.length()),
				headerValues.get("numberOfLegsEncoded").getIntegerValue());

		System.out.println(registers);

	}

}
