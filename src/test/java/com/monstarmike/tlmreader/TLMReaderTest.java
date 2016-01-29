package com.monstarmike.tlmreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TLMReaderTest extends TestCase {

	String tlmFileSailplane = "src/test/data/2015 - FSS 2 - day 2.TLM";
	String tlmFileHeli = "src/test/data/2015-12-22_HELI.TLM";

	List<Long> expectedFlightDurationsSailplane = new ArrayList<Long>(
			Arrays.<Long> asList(762050l, 0l, 838960l, 510170l, 655640l, 861870l, 889210l, 838040l));
	List<Integer> expectedFlightDataBlocksSailplane = new ArrayList<Integer>(
			Arrays.asList(52236, 0, 52506, 38117, 50823, 54373, 49197, 42081));
	List<Long> expectedFlightDurationsHeli = new ArrayList<Long>(
			Arrays.<Long> asList(454610l, 9590l, 366530l, 348790l, 352780l));
	List<Integer> expectedFlightDataBlocksHeli= new ArrayList<Integer>(
			Arrays.asList(79684, 1746, 64788, 60682, 61738));

	public void testParseFlightDefinitionsTestDurationsWithInputStream() throws IOException {
		TLMReader reader = new TLMReader();
		InputStream inputStream = new FileInputStream(new File(tlmFileSailplane));
		List<IFlight> flights = reader.parseFlightDefinitions(inputStream);
		List<Long> actualFlightDurations = new ArrayList<Long>();
		for (IFlight flight : flights) {
			actualFlightDurations.add(flight.get_duration().getMillis());
		}
		Assert.assertEquals(expectedFlightDurationsSailplane, actualFlightDurations);
	}

	public void testParseFlightDefinitionsTestDurationsSailPlain() throws IOException {
		TLMReader reader = new TLMReader();
		List<IFlight> flights = reader.parseFlightDefinitions(tlmFileSailplane);
		List<Long> actualFlightDurations = new ArrayList<Long>();
		for (IFlight flight : flights) {
			actualFlightDurations.add(flight.get_duration().getMillis());
		}
		Assert.assertEquals(expectedFlightDurationsSailplane, actualFlightDurations);
	}
	public void testParseFlightDefinitionsTestDataBlocksSailplane() throws IOException {
		TLMReader reader = new TLMReader();
		List<IFlight> flights = reader.parseFlightDefinitions(tlmFileSailplane);
		List<Integer> actutalDataBlocks = new ArrayList<Integer>();
		for (IFlight flight : flights) {
			actutalDataBlocks.add(flight.getNumberOfDataBlocks());
		}
		Assert.assertEquals(expectedFlightDataBlocksSailplane, actutalDataBlocks);
	}

	public void testParseFlightDefinitionsTestDurationsHeli() throws IOException {
		TLMReader reader = new TLMReader();
		List<IFlight> flights = reader.parseFlightDefinitions(tlmFileHeli);
		List<Long> actualFlightDurations = new ArrayList<Long>();
		for (IFlight flight : flights) {
			actualFlightDurations.add(flight.get_duration().getMillis());
		}
		Assert.assertEquals(expectedFlightDurationsHeli, actualFlightDurations);
	}
	
	public void testParseFlightDefinitionsTestDataBlocksHeli() throws IOException {
		TLMReader reader = new TLMReader();
		List<IFlight> flights = reader.parseFlightDefinitions(tlmFileHeli);
		List<Integer> actutalDataBlocks = new ArrayList<Integer>();
		for (IFlight flight : flights) {
			actutalDataBlocks.add(flight.getNumberOfDataBlocks());
		}
		Assert.assertEquals(expectedFlightDataBlocksHeli, actutalDataBlocks);
	}

	public void testParseFlightTestDurationWithInputStream() throws IOException {
		int flightId = 0;
		TLMReader reader = new TLMReader();
		InputStream inputStream = new FileInputStream(new File(tlmFileSailplane));
		Flight flight = reader.parseFlight(inputStream, flightId);
		Assert.assertEquals(expectedFlightDurationsSailplane.get(flightId), Long.valueOf(flight.get_duration().getMillis()));
	}

	public void testParseFlightFlightIdNotAvailable() throws IOException {
		int flightId = 20;
		TLMReader reader = new TLMReader();
		try {
			reader.parseFlight(tlmFileSailplane, flightId);
			Assert.fail("parseFlight should throw a RuntimeException if the flightId is not available");
		} catch (RuntimeException e) {
			// The expected result
		}
	}
}
