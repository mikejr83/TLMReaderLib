package com.monstarmike.tlmreader;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.StandardBlock;

public class App {

	public static void main(String[] args) {
		TLMReader reader = new TLMReader();
		try {
			String tlmFileSailplane = "src/test/data/2015 - FSS 2 - day 2.TLM";
			String tlmFileHeli = "src/test/data/2015-12-29.TLM";
			List<IFlight> flights = reader.parseFlightDefinitions(tlmFileHeli);
			for (IFlight flight : flights) {
				printFlightDefinitions(flight);
			}
			Flight flight = reader.parseFlight(tlmFileSailplane, 0);
			printFlightDefinitions(flight);
//			printDataBlocks(flight);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printFlightDefinitions(IFlight flight) {
		System.out.println("FlightHeader; Duration: " + duration(flight.get_duration()) + ", duration in millis: "
				+ flight.get_duration().getMillis() + ", expectedNumberOfBlocks: " + flight.getNumberOfDataBlocks());
	}

	private static void printDataBlocks(Flight flight) {
		Iterator<DataBlock> dataBlockIterator = flight.get_dataBlocks();
		while (dataBlockIterator.hasNext()) {
			DataBlock dataBlock = dataBlockIterator.next();
			if (dataBlock instanceof StandardBlock) {
				StandardBlock standardBlock = (StandardBlock) dataBlock;
				System.out.println("Std: rpm: " + standardBlock.get_rpm() + " volt: " + standardBlock.get_volt()
						+ " temp: " + standardBlock.get_temperature());
			}
		}
	}

	private static String duration(Duration flightDuration) {
		Period period = flightDuration.toPeriod();
		PeriodFormatter hms = new PeriodFormatterBuilder().appendHours().appendSeparator(":").printZeroAlways()
				.appendMinutes().appendSeparator(":").appendSecondsWithMillis().toFormatter();

		return hms.print(period);
	}
}
