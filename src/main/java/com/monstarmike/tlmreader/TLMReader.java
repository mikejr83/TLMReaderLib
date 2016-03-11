package com.monstarmike.tlmreader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderBlock;
import com.monstarmike.tlmreader.datablock.HeaderDataBlock;
import com.monstarmike.tlmreader.datablock.HeaderNameBlock;
import com.monstarmike.tlmreader.datablock.HeaderRpmBlock;
import com.monstarmike.tlmreader.datablock.HeaderRxBlock;
import com.monstarmike.tlmreader.datablock.HeaderVoltBlock;

/**
 * This Class is responsible for parsing TLM files. <br>
 * The usage of this class is:<br>
 * <ul>
 * <li>First parse the whole TLM file and returns a list file
 * {@link FlightDefinition} s with the method 'parseFlightDefinitions'. The
 * {@link FlightDefinition} contains information about the flight with
 * HeaderBlocks, but without datablocks.</li>
 * <li>Second get the all details of a specific {@link Flight} with a call of
 * the method 'parseFlight'.</li>
 * </ul>
 */
public class TLMReader {

	private int byteCounter;

	/**
	 * @return The size of the TLM file.
	 */
	public int getNumberOfBytesRead() {
		return byteCounter;
	}

	/**
	 * The method parse the given file and returns a lists of {@link IFlight}
	 * s. <br>
	 * To reduce the memory usage with large TLM files, the {@link IFlight}
	 * Interface is used with lightweight {@link FlightDefinition} instances.
	 * 
	 * @param path
	 *               The path of the file to parse.
	 * @return A list of {@link IFlight} objects (they do no contain
	 *         dataBlocks
	 * @throws IOException
	 *                If the file could not be read successfully, then a
	 *                {@link IOException} is thrown.
	 */
	public List<IFlight> parseFlightDefinitions(String path) throws IOException {
		return this.parseFlightDefinitions(new FileInputStream(new File(path)));
	}

	/**
	 * Reads the specified flight with all details from a given file.
	 * 
	 * @param path
	 *               The path of the file to parse.
	 * @param index
	 *               The index of the flight which should be read. The first
	 *               flight has the index 0.
	 * @return The requested {@link Flight} with all details
	 * @throws IOException
	 *                If the file could not be read successfully, then a
	 *                {@link IOException} is thrown.
	 */
	public Flight parseFlight(String path, int index) throws IOException {
		return this.parseFlight(new FileInputStream(new File(path)), index);
	}

	/**
	 * The method parse the given {@link InputStream} and returns a lists of
	 * {@link IFlight}s. <br>
	 * To reduce the memory usage with large TLM files, the {@link IFlight}
	 * Interface is used with lightweight {@link FlightDefinition} instances.
	 * 
	 * @param inputStream
	 *               The {@link InputStream} to parse.
	 * @return A list of {@link IFlight} objects (they do no contain
	 *         dataBlocks
	 * @throws IOException
	 *                If the file could not be read successfully, then a
	 *                {@link IOException} is thrown.
	 */
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
				DataBlock createdDataBlock = DataBlock.createDataBlock(dataBytes,
						getCurrentFlight().getRpmHeader());
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

	/**
	 * Reads the specified flight with all details from a given
	 * {@link InputStream}.
	 * 
	 * @param inputStream
	 *               The {@link InputStream} to parse.
	 * @param index
	 *               The index of the flight which should be read. The first
	 *               flight has the index 0.
	 * @return The requested {@link Flight} with all details
	 * @throws IOException
	 *                If the file could not be read successfully, then a
	 *                {@link IOException} is thrown.
	 */
	public Flight parseFlight(InputStream inputStream, final int index) throws IOException {
		final ArrayList<Flight> flights = new ArrayList<Flight>();
		new TlmParser() {

			@Override
			public void handleHeaderBlock(byte[] headerBytes, int flightNumber) {
				if (flightNumber != index) {
					return;
				}
				if (HeaderNameBlock.isHeaderName(headerBytes)) {
					flights.add(new Flight());
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
			public void handleDataBlock(byte[] dataBytes, int flightNumber) {
				if (flightNumber != index) {
					return;
				}
				DataBlock createdDataBlock = DataBlock.createDataBlock(dataBytes,
						getCurrentFlight().getRpmHeader());
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
			throw new RuntimeException("FlightId  '" + index + "' not found in inputStream.");
		}
		Flight flight = flights.get(flights.size() - 1);
		flight.normalizeDataBlocks();
		return flight;
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