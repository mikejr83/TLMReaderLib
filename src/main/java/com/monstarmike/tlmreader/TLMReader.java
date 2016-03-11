package com.monstarmike.tlmreader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
		return new TlmFlightDefinitionParser().parseFlightDefinitions(new BufferedInputStream(inputStream));
	}

	/**
	 * Reads the specified flight with all details from a given
	 * {@link InputStream}. <br>
	 * Remove all not normal dataBlocks (with invalid bad values).
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
		Flight flight = new TlmFlightParser().parseFlight(new BufferedInputStream(inputStream), index);
		if (flight == null) {
			throw new RuntimeException("FlightId  '" + index + "' not found in inputStream.");
		}
		flight.normalizeDataBlocks();
		return flight;
	}

}