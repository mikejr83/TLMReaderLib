package com.monstarmike.tlmreader;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TLMReaderTest {

	String tlmFileSailplane = "src/test/data/2015 - FSS 2 - day 2.TLM";
	String tlmFileHeli = "src/test/data/2015-12-22_HELI.TLM";

	List<Long> expectedFlightDurationsSailplane = new ArrayList<Long>(
			Arrays.<Long> asList(762050l, 0l, 838960l, 510170l, 655640l, 861870l, 889210l, 838040l));
	List<Integer> expectedFlightDataBlocksSailplane = new ArrayList<Integer>(
			Arrays.asList(56960, 0, 57250, 41557, 55404, 59186, 53659, 45903));
	List<Long> expectedFlightDurationsHeli = new ArrayList<Long>(
			Arrays.<Long> asList(454610l, 9590l, 366530l, 348790l, 352780l));
	List<Integer> expectedFlightDataBlocksHeli = new ArrayList<Integer>(
			Arrays.asList(79684, 1746, 64788, 60682, 61738));

	@Test
	public void testParseFlightDefinitionsTestDurationsWithInputStream() throws IOException {
		TLMReader reader = new TLMReader();
		InputStream inputStream = new FileInputStream(new File(tlmFileSailplane));
		List<IFlight> flights = reader.parseFlightDefinitions(inputStream);
		List<Long> actualFlightDurations = new ArrayList<Long>();
		for (IFlight flight : flights) {
			actualFlightDurations.add(flight.getDuration().getMillis());
		}
		assertEquals(expectedFlightDurationsSailplane, actualFlightDurations);
	}

	@Test
	public void testParseFlightDefinitionsTestDurationsSailPlain() throws IOException {
		TLMReader reader = new TLMReader();
		List<IFlight> flights = reader.parseFlightDefinitions(tlmFileSailplane);
		List<Long> actualFlightDurations = new ArrayList<Long>();
		for (IFlight flight : flights) {
			actualFlightDurations.add(flight.getDuration().getMillis());
		}
		assertEquals(expectedFlightDurationsSailplane, actualFlightDurations);
	}

	@Test
	public void testParseFlightDefinitionsTestDataBlocksSailplane() throws IOException {
		TLMReader reader = new TLMReader();
		List<IFlight> flights = reader.parseFlightDefinitions(tlmFileSailplane);
		List<Integer> actutalDataBlocks = new ArrayList<Integer>();
		for (IFlight flight : flights) {
			actutalDataBlocks.add(flight.getNumberOfDataBlocks());
		}
		assertEquals(expectedFlightDataBlocksSailplane, actutalDataBlocks);
	}

	@Test
	public void testParseFlightDefinitionsTestDurationsHeli() throws IOException {
		TLMReader reader = new TLMReader();
		List<IFlight> flights = reader.parseFlightDefinitions(tlmFileHeli);
		List<Long> actualFlightDurations = new ArrayList<Long>();
		for (IFlight flight : flights) {
			actualFlightDurations.add(flight.getDuration().getMillis());
		}
		assertEquals(expectedFlightDurationsHeli, actualFlightDurations);
	}

	public void testParseFlightDefinitionsTestDataBlocksHeli() throws IOException {
		TLMReader reader = new TLMReader();
		List<IFlight> flights = reader.parseFlightDefinitions(tlmFileHeli);
		List<Integer> actutalDataBlocks = new ArrayList<Integer>();
		for (IFlight flight : flights) {
			actutalDataBlocks.add(flight.getNumberOfDataBlocks());
		}
		assertEquals(expectedFlightDataBlocksHeli, actutalDataBlocks);
	}

	public void testParseFlightTestDurationWithInputStream() throws IOException {
		int flightId = 0;
		TLMReader reader = new TLMReader();
		InputStream inputStream = new FileInputStream(new File(tlmFileSailplane));
		Flight flight = reader.parseFlight(inputStream, flightId);
		assertEquals(expectedFlightDurationsSailplane.get(flightId), Long.valueOf(flight.getDuration().getMillis()));
	}

	@Test(expected = RuntimeException.class)
	public void testParseFlightFlightIdNotAvailable() throws IOException {
		int flightId = 20;
		TLMReader reader = new TLMReader();
		reader.parseFlight(tlmFileSailplane, flightId);
	}
}
