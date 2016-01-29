package com.monstarmike.tlmreader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderDataBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;
import com.monstarmike.tlmreader.datablock.HeaderRpmBlock;
import com.monstarmike.tlmreader.datablock.HeaderVoltBlock;

public class TLMReader {
	private int byteCounter;

	public int getNumberOfBytesRead() {
		return byteCounter;
	}

	public List<IFlight> parseFlightDefinitions(String path) throws IOException {
		return this.parseFlightDefinitions(new FileInputStream(new File(path)));
	}

	public Flight parseFlight(String path, int joId) throws IOException {
		return this.parseFlight(new FileInputStream(new File(path)), joId);
	}

	public List<IFlight> parseFlightDefinitions(InputStream inputStream) throws IOException {
		final ArrayList<IFlight> flights = new ArrayList<IFlight>();
		new TlmParser() {

			@Override
			public void handleHeaderBlock(byte[] headerBytes, int flightNumber) {
				if (HeaderNameBlock.isHeaderName(headerBytes)) {
					flights.add(new FlightDefinition());
					getCurrentFlight().addHeaderNameBlock(new HeaderNameBlock(headerBytes));
				} else if (HeaderRpmBlock.isRpmHeader(headerBytes)) {
					getCurrentFlight().addRpmHeaderBlock(new HeaderRpmBlock(headerBytes));
				}
			}

			@Override
			public void handleDataBlock(byte[] dataBytes, int flightNumber) {
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
		}.parseStream(new BufferedInputStream(inputStream));
		return flights;
	}

	public Flight parseFlight(InputStream inputStream, final int joId) throws IOException {
		final ArrayList<Flight> flights = new ArrayList<Flight>();
		new TlmParser() {

			@Override
			public void handleHeaderBlock(byte[] headerBytes, int flightNumber) {
				if (flightNumber != joId) {
					return;
				}
				if (HeaderNameBlock.isHeaderName(headerBytes)) {
					flights.add(new Flight());
					getCurrentFlight().addHeaderNameBlock(new HeaderNameBlock(headerBytes));
				} else if (HeaderRpmBlock.isRpmHeader(headerBytes)) {
					getCurrentFlight().addRpmHeaderBlock(new HeaderRpmBlock(headerBytes));
				} else if (HeaderVoltBlock.isVoltHeader(headerBytes)) {
					getCurrentFlight().addHeaderBlock(new HeaderVoltBlock(headerBytes));
				} else {
					getCurrentFlight().addHeaderBlock(new HeaderDataBlock(headerBytes));
				}
			}

			@Override
			public void handleDataBlock(byte[] dataBytes, int flightNumber) {
				if (flightNumber != joId) {
					return;
				}
				DataBlock createdDataBlock = DataBlock.createDataBlock(dataBytes, getCurrentFlight().getRpmHeader());
				if (createdDataBlock != null) {
					getCurrentFlight().addDataBlock(createdDataBlock);
				}
			}

			private Flight getCurrentFlight() {
				if (flights.isEmpty()) {
					throw new RuntimeException("No current Flight available");
				}
				return flights.get(flights.size() - 1);
			}
		}.parseStream(new BufferedInputStream(inputStream));
		if (flights.isEmpty()) {
			throw new RuntimeException("FlightId  '"+joId+"' not found in inputStream.");
		}
		return flights.get(flights.size() - 1);
	}

	private abstract class TlmParser {

		public void parseStream(BufferedInputStream bufferedStream) throws IOException {
			int flightNumber = -1;
			byte[] headerTest = new byte[4];
			bufferedStream.mark(4);
			while (bufferedStream.read(headerTest, 0, 4) == 4) {
				bufferedStream.reset();
				if (HeaderBlock.isHeaderBlock(headerTest)) {
					byte[] headerBytes = new byte[36];
					byteCounter += bufferedStream.read(headerBytes, 0, 36);
					if (HeaderNameBlock.isHeaderName(headerBytes)) {
						flightNumber++;
					}
					handleHeaderBlock(headerBytes, flightNumber);
				} else {
					byte[] dataBytes = new byte[20];
					byteCounter += bufferedStream.read(dataBytes, 0, 20);
					handleDataBlock(dataBytes, flightNumber);
				}
				bufferedStream.mark(4);
			}
		}

		public abstract void handleDataBlock(byte[] dataBytes, int flightNumber);

		public abstract void handleHeaderBlock(byte[] headerBytes, int flightNumber);
	}

}
