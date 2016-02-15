package com.monstarmike.tlmreader;

import java.io.IOException;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.RXBlock;
import com.monstarmike.tlmreader.datablock.StandardBlock;

public class App {

	public static void main(String[] args) {
		TLMReader reader = new TLMReader();
		try {
			String tlmFileSailplane = "src/test/data/2015 - FSS 2 - day 2.TLM";
			String tlmFileHeli = "src/test/data/2015-12-22_HELI.TLM";
			String tlm = tlmFileHeli;
			List<IFlight> flights = reader.parseFlightDefinitions(tlm);
			for (IFlight flight : flights) {
				printFlightDefinitions(flight);
			}
			Flight flight = reader.parseFlight(tlm, 0);
			printFlightDefinitions(flight);
			printDataBlocks(flight);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("MAX: " + Short.MAX_VALUE);
	}

	private static void printFlightDefinitions(IFlight flight) {
		System.out.println("FlightHeader; Duration: " + duration(flight.getDuration()) + ", duration in millis: "
				+ flight.getDuration().getMillis() + ", expectedNumberOfBlocks: " + flight.getNumberOfDataBlocks());
	}

	private static void printDataBlocks(Flight flight) {
		int lastTimestamp = 0;
		for (DataBlock dataBlock : flight.getDataBlocks()) {
			// if (dataBlock instanceof StandardBlock) {
			// System.out.println("Timestamp: " + dataBlock.getTimestamp());
			// if (lastTimestamp >= dataBlock.getTimestamp()) {
			// System.out.println(
			// " ---------------- last: " + lastTimestamp + " current: " +
			// dataBlock.getTimestamp());
			// }
			// }
//			printStandardBlock(dataBlock);
			printRxBlock(dataBlock);
		}
	}

	private static void printRxBlock(DataBlock dataBlock) {
		if (dataBlock instanceof RXBlock) {
			RXBlock rxBlock = (RXBlock) dataBlock;
			System.out.println(rxBlock);
//			System.out.println("A: " + rxBlock.getLostPacketsReceiverA() + ", B: " + rxBlock.getLostPacketsReceiverB() + ", L: " + rxBlock.getLostPacketsReceiverL()
//					+ ", R: " + rxBlock.getLostPacketsReceiverR() + ", FrameLoss: " + rxBlock.getFrameLoss() + ", Holds: "
//					+ rxBlock.getHolds());
		}
	}

	private static void printStandardBlock(DataBlock dataBlock) {
		if (dataBlock instanceof StandardBlock) {
			StandardBlock standardBlock = (StandardBlock) dataBlock;
			System.out.println("Std: rpm: " + standardBlock.getRpm() + "(" + standardBlock.hasValidRpmData() + ") volt: "
					+ standardBlock.getVoltageInHunderthOfVolts() + "(" + standardBlock.hasValidVoltageData() + ") temp: "
					+ standardBlock.getTemperatureInGradFahrenheit() + "(" + standardBlock.hasValidTemperatureData()
					+ ")");
		}
	}

	private static String duration(Duration flightDuration) {
		Period period = flightDuration.toPeriod();
		PeriodFormatter hms = new PeriodFormatterBuilder().appendHours().appendSeparator(":").printZeroAlways()
				.appendMinutes().appendSeparator(":").appendSecondsWithMillis().toFormatter();

		return hms.print(period);
	}
}
