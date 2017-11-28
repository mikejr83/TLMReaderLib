package com.monstarmike.tlmreader;

import java.io.BufferedInputStream;
import java.io.IOException;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderDataBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;
import com.monstarmike.tlmreader.datablock.HeaderRpmBlock;
import com.monstarmike.tlmreader.datablock.HeaderRxBlock;
import com.monstarmike.tlmreader.datablock.HeaderVoltBlock;

final class TlmFlightParser extends TlmParser {
	private int index;
	private Flight flight;

	@Override
	protected void handleHeaderBlock(byte[] headerBytes, int flightNumber) {
		if (flightNumber != index) {
			return;
		}
		if (HeaderNameBlock.isHeaderName(headerBytes)) {
			flight = new Flight();
			getCurrentFlight().addHeaderNameBlock(new HeaderNameBlock(headerBytes));
		} else if (HeaderRpmBlock.isRpmHeader(headerBytes)) {
			getCurrentFlight().addRpmHeaderBlock(new HeaderRpmBlock(headerBytes));
		} else if (HeaderVoltBlock.isVoltHeader(headerBytes)) {
			getCurrentFlight().addHeaderBlock(new HeaderVoltBlock(headerBytes));
		} else if (HeaderRxBlock.isRxHeader(headerBytes)) {
			getCurrentFlight().addHeaderBlock(new HeaderRxBlock(headerBytes));
		} else {
			getCurrentFlight().addHeaderBlock(new HeaderDataBlock(headerBytes));
		}
	}

	@Override
	protected void handleDataBlock(byte[] dataBytes, int flightNumber) {
		if (flightNumber != index) {
			return;
		}
		DataBlock createdDataBlock = DataBlock.createDataBlock(dataBytes, getCurrentFlight().getRpmHeader());
		if (createdDataBlock != null) {
			getCurrentFlight().addDataBlock(createdDataBlock);
		}
	}

	private Flight getCurrentFlight() {
		if (flight == null) {
			throw new RuntimeException("No current Flight available");
		}
		return flight;
	}

	public Flight parseFlight(BufferedInputStream bufferedStream, int index) throws IOException {
		this.index = index;
		parseStream(bufferedStream);
		return flight;
	}
}