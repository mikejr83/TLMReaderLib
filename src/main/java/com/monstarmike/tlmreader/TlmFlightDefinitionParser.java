package com.monstarmike.tlmreader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;
import com.monstarmike.tlmreader.datablock.HeaderRpmBlock;

final class TlmFlightDefinitionParser extends TlmParser {
	private final ArrayList<IFlight> flights = new ArrayList<>();

	TlmFlightDefinitionParser() {
	}

	@Override
	protected void handleHeaderBlock(byte[] headerBytes, int flightNumber) {
		if (HeaderNameBlock.isHeaderName(headerBytes)) {
			flights.add(new FlightDefinition());
			getCurrentFlight().addHeaderNameBlock(new HeaderNameBlock(headerBytes));
		} else if (HeaderRpmBlock.isRpmHeader(headerBytes)) {
			getCurrentFlight().addRpmHeaderBlock(new HeaderRpmBlock(headerBytes));
		}
	}

	@Override
	protected void handleDataBlock(byte[] dataBytes, int flightNumber) {
		DataBlock createdDataBlock = DataBlock.createDataBlock(dataBytes, getCurrentFlight().getRpmHeader());
		if (createdDataBlock != null) {
			getCurrentFlight().addDataBlock(createdDataBlock);
		}
	}

	private IFlight getCurrentFlight() {
		if (flights.isEmpty()) {
			throw new RuntimeException("No current Flight available");
		}
		return flights.get(flights.size() - 1);
	}

	public List<IFlight> parseFlightDefinitions(BufferedInputStream bufferedStream) throws IOException {
		parseStream(bufferedStream);
		return flights;
	}
}