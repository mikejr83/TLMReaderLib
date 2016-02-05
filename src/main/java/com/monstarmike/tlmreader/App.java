package com.monstarmike.tlmreader;

import java.io.IOException;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.RXBlock;

public class App {

	public static void main(String[] args) {
		TLMReader reader = new TLMReader();
		try {
//			String tlmFileSailplane = "src/test/data/2015 - FSS 2 - day 2.TLM";
			String tlmFileHeli = "src/test/data/2015-12-22.TLM";
			List<IFlight> flights = reader.parseFlightDefinitions(tlmFileHeli);
			for (IFlight flight : flights) {
				printFlightDefinitions(flight);
			}
			Flight flight = reader.parseFlight(tlmFileHeli, 0);
			printFlightDefinitions(flight);
			printDataBlocks(flight);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printFlightDefinitions(IFlight flight) {
		System.out.println("FlightHeader; Duration: " + duration(flight.getDuration()) + ", duration in millis: "
				+ flight.getDuration().getMillis() + ", expectedNumberOfBlocks: " + flight.getNumberOfDataBlocks());
	}

	private static void printDataBlocks(Flight flight) {
		for (DataBlock dataBlock : flight.getDataBlocks()) {
			// if (dataBlock instanceof StandardBlock) {
			// StandardBlock standardBlock = (StandardBlock) dataBlock;
			// System.out.println("Std: rpm: " + standardBlock.get_rpm() + "
			// volt: " + standardBlock.get_volt()
			// + " temp: " + standardBlock.get_temperatureInGradCelsius());
			// }
			if (dataBlock instanceof RXBlock) {
				RXBlock rxBlock = (RXBlock) dataBlock;
				System.out.println("A: " + rxBlock.getA() + ", B: " + rxBlock.getB() + ", L: " + rxBlock.getL()
						+ ", R: " + rxBlock.getR() + ", FrameLoss: " + rxBlock.getFrameLoss() + ", Holds: "
						+ rxBlock.getHolds());
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
