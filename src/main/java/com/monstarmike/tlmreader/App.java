package com.monstarmike.tlmreader;

import java.io.IOException;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.monstarmike.tlmreader.datablock.AirspeedBlock;
import com.monstarmike.tlmreader.datablock.AltitudeBlock;
import com.monstarmike.tlmreader.datablock.AltitudeZeroBlock;
import com.monstarmike.tlmreader.datablock.CurrentBlock;
import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.EscBlock;
import com.monstarmike.tlmreader.datablock.FlightPackBlock;
import com.monstarmike.tlmreader.datablock.GForceBlock;
import com.monstarmike.tlmreader.datablock.GPSCollectorBlock;
import com.monstarmike.tlmreader.datablock.GPSLocationBlock;
import com.monstarmike.tlmreader.datablock.GPSStatusBlock;
import com.monstarmike.tlmreader.datablock.JetCatBlock;
import com.monstarmike.tlmreader.datablock.PowerBoxBlock;
import com.monstarmike.tlmreader.datablock.RxBlock;
import com.monstarmike.tlmreader.datablock.ServoDataBlock;
import com.monstarmike.tlmreader.datablock.StandardBlock;
import com.monstarmike.tlmreader.datablock.TemperatureBlock;
import com.monstarmike.tlmreader.datablock.VarioBlock;
import com.monstarmike.tlmreader.datablock.VoltageBlock;

public class App {

	public static void main(String[] args) {
		TLMReader reader = new TLMReader();
		try {
			long start = System.currentTimeMillis();
			String tlmFileSailplane = "src/test/data/2015-FSS2-day2.TLM";
			String tlmFileHeli = "src/test/data/2015-12-22_HELI.TLM";
			// String tlmFileHeli = "src/test/data/2015-12-29.TLM";
			// String tlmFileHeli = "src/test/data/20160129.TLM";
			String tlm = tlmFileHeli;
			List<IFlight> flights = reader.parseFlightDefinitions(tlm);
			for (IFlight flight : flights) {
				printFlightDefinitions(flight);
			}
			Flight flight = reader.parseFlight(tlm, flights.size() - 1);
			flight.removeRedundantDataBlocks();
			printFlightDefinitions(flight);
			printDataBlocks(flight);
			long end = System.currentTimeMillis();
			System.out.println("duration: " + (end - start) + " ms");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printFlightDefinitions(IFlight flight) {
		System.out.println("FlightHeader; Duration: " + duration(flight.getDuration()) + ", duration in millis: "
				+ flight.getDuration().getMillis() + ", expectedNumberOfBlocks: " + flight.getNumberOfDataBlocks());
	}

	private static void printDataBlocks(Flight flight) {
		for (DataBlock dataBlock : flight.getDataBlocks()) {
			if (dataBlock instanceof StandardBlock) {
				System.out.println(((StandardBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof RxBlock) {
				System.out.println(((RxBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof VarioBlock) {
				System.out.println(((VarioBlock) dataBlock).toString());
			}
			//primitive data blocks
			else if (dataBlock instanceof AltitudeBlock) {
				System.out.println(((AltitudeBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof AltitudeZeroBlock) {
				System.out.println(((AltitudeZeroBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof VoltageBlock) {
				System.out.println(((VoltageBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof CurrentBlock) {
				System.out.println(((CurrentBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof TemperatureBlock) {
				System.out.println(((TemperatureBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof AirspeedBlock) {
				System.out.println(((AirspeedBlock) dataBlock).toString());
			}
			//other important data blocks
			else if (dataBlock instanceof GPSLocationBlock) {
				System.out.println(((GPSLocationBlock) dataBlock).toString());
				GPSCollectorBlock.getInstance().updateLocation((GPSLocationBlock) dataBlock);
				if (GPSCollectorBlock.getInstance().isUpdated()) {
					System.out.println(GPSCollectorBlock.getInstance().toString());
				}
			}
			else if (dataBlock instanceof GPSStatusBlock) {
				System.out.println(((GPSStatusBlock) dataBlock).toString());
				GPSCollectorBlock.getInstance().updateStatus((GPSStatusBlock) dataBlock);
				if (GPSCollectorBlock.getInstance().isUpdated()) {
					System.out.println(GPSCollectorBlock.getInstance().toString());
				}
			}
			else if (dataBlock instanceof FlightPackBlock) {
				System.out.println(((FlightPackBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof EscBlock) {
				System.out.println(((EscBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof PowerBoxBlock) {
				System.out.println(((PowerBoxBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof JetCatBlock) {
				System.out.println(((JetCatBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof GForceBlock) {
				System.out.println(((GForceBlock) dataBlock).toString());
			}
			else if (dataBlock instanceof ServoDataBlock) {
				System.out.println(((ServoDataBlock) dataBlock).toString());
			}
			else //undefined dataBlocks
				System.out.println(dataBlock.toString());

		}
	}

	private static String duration(Duration flightDuration) {
		Period period = flightDuration.toPeriod();
		PeriodFormatter hms = new PeriodFormatterBuilder().appendHours().appendSeparator(":").printZeroAlways()
				.appendMinutes().appendSeparator(":").appendSecondsWithMillis().toFormatter();

		return hms.print(period);
	}
}
