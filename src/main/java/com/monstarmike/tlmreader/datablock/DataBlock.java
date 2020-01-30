package com.monstarmike.tlmreader.datablock;

import java.util.ArrayList;
import java.util.List;

import com.google.common.primitives.Ints;
import com.sun.istack.Nullable;

public abstract class DataBlock implements IBlock{

	public enum SensorType {
		NODATA(0x00),										// No data in packet, but telemetry is alive
		VOLTAGE(0x01),									// High-Voltage sensor (INTERNAL)
		TEMPERATURE(0x02),							// Temperature Sensor (INTERNAL)
		AMPS(0x03),											// Amps (INTERNAL)
		FLITECTRL(0x05),								// Flight Controller Status Report
		PBOX(0x0A),											// PowerBox
		LAPTIMER(0x0B),									// Lap Timer
		VTX	(0x0D),											// Video Transmitter Feedback
		AIRSPEED(0x11),									// Air Speed (Eagle Tree Sensor)
		ALTITUDE(0x12),									// Altitude (Eagle Tree Sensor)
		GMETER(0x14),										// G-Force (Eagle Tree Sensor)
		JETCAT(0x15),										// Turbine interface (Eagle Tree)
		GPS_LOC(0x16),									// GPS Location Data (Eagle Tree)
		GPS_STATS(0x17),								// GPS Status (Eagle Tree)
		RX_MAH(0x18),										// Receiver Pack Capacity (Dual)
		JETCAT_2(0x19),									// Turbine interface, message 2 format (Eagle Tree)
		GYRO(0x1A),											// 3-axis gyro
		ATTMAG(0x1B),										// Attitude and Magnetic Compass
		TILT(0x1C),											// Surface Tilt Sensor
		AS6X_GAIN(0x1E),								// Active AS6X Gains (new mode)
		AS3X_LEGACYGAIN(0x1F),					// Active AS3X Gains for legacy mode
		ESC(0x20),											// Electronic Speed Control
		FUEL(0x22),											// Fuel Flow Meter
		ALPHA6(0x24),										// Alpha6 Stabilizer
		GPS_BINARY(0x26),								// GPS, binary format
		FLIGHT_PACK(0x34),							// Flight Battery Capacity (Dual)
		DIGITAL_AIR(0x36),							// Digital Inputs & Tank Pressure
		STRAIN(0x38),										// Thrust/Strain Gauge
		LIPOMON(0x3A),									// 6S Cell Monitor (LiPo taps)
		LIPOMON_14(0x3F),								// 14S Cell Monitor (LiPo taps)
		VARIO_S(0x40),									// Vario
		SMARTBATT(0x42),								// Spektrum SMART Battery (multiple structs)
		VSPEAK(0x60),										// Reserved for V-Speak
		SMOKE_EL(0x61),									// Reserved for Smoke-EL.de
		CROSSFIRE(0x62),								// Reserved for Crossfire devices
		ALT_ZERO(0x7B),									// Pseudo-device setting Altitude "zero"
		RTC(0x7C),											// Pseudo-device giving timestamp
		FRAMEDATA(0x7D),								// Transmitter frame data
		RPM(0x7E),											// RPM sensor
		QOS(0x7F);											// RxV + flight log data

		final int id;
		final String name;
		public static final SensorType VALUES[]	= values();	// use this to avoid cloning if calling values()
		
		SensorType(int value) {
			this.id = value;
			this.name = this.name();
		}

		@Nullable
		public static SensorType fromSensorByte(byte sensorByte) {
			for (SensorType sensor : SensorType.VALUES) {
				if (sensor.id == sensorByte)
					return sensor;
			}
			return NODATA;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
	}

	private static int currentSeq = 0;
	
	protected int sequence = currentSeq++; 
	protected int timestamp;
	
	protected List<String> 	measurementNames = new ArrayList<String>();
	protected List<String> 	measurementUnits = new ArrayList<String>();
	protected List<Double> 	measurementFactors = new ArrayList<Double>();
	protected List<Integer> measurementValues = new ArrayList<Integer>();

	/**
	 * default constructor used for GPSCollectorBlock only
	 */
	protected DataBlock() {};

	/**
	 * normal constructor to be used to fill internal values like timestamp
	 * @param rawData
	 */
	public DataBlock(byte[] rawData) {
		decode(rawData);
	}
	
	/**
	 * copy constructor only used in GPSCollectorBlock
	 * @param that
	 */
	protected DataBlock(DataBlock that) {
		this.timestamp = that.timestamp;
		this.sequence = that.sequence;
	}

	public int getSequence() {
		return sequence;
	}
	
	/**
	 * Timestamp in hunderth of seconds
	 */
	public int getTimestamp() {
		return timestamp;
	}

	private void decode(byte[] rawData) {
		timestamp = Ints.fromBytes(rawData[3], rawData[2], rawData[1], rawData[0]);
	}

	public static DataBlock createDataBlock(byte[] bytes, HeaderRpmBlock rpmHeaderBlock) {
		DataBlock block = null;
		short tm1100 = (short) (bytes[4] & 0xFF);
		if (tm1100 == 0xFE || tm1100 == 0xFF) {
			switch (tm1100) {
			case 0xFE:
				block = new StandardBlock(bytes, rpmHeaderBlock);
				break;

			case 0xFF:
				block = new RxBlock(bytes);
				break;
			}
		} else if (bytes[4] == 0x09 && bytes[5] == 0x06) {
			block = new ServoDataBlock(bytes);
		} else {
			switch (SensorType.fromSensorByte(bytes[4])) {
			default:
			case NODATA:					// unknown
				//System.err.println(String.format("unknown sensorByte 0x%02X", bytes[4]));
				break;

			case VOLTAGE:
				block = new VoltageBlock(bytes);
				break;

			case TEMPERATURE:
				block = new TemperatureBlock(bytes);
				break;

			case AMPS:						// current (amperage) usage
				block = new CurrentBlock(bytes);
				break;

			case PBOX:						// powerbox
				block = new PowerBoxBlock(bytes);
				break;

			case AIRSPEED:				// airspeed
				block = new AirspeedBlock(bytes);
				break;

			case ALTITUDE:				// altitude
				block = new AltitudeBlock(bytes);
				break;

			case GMETER:					// g-force
				block = new GForceBlock(bytes);
				break;
				
			case JETCAT:					// JetCatBlock
				block = new JetCatBlock(bytes);
				break;
				
			case GPS_LOC:					// GPS Location Data
				block = new GPSLocationBlock(bytes);
				break;
				
			case GPS_STATS:				// GPS Status
				block = new GPSStatusBlock(bytes);
				break;
				
			case RX_MAH:					// Receiver Pack Capacity (Dual)
				block = new RxPackCapacityBlock(bytes);
				break;

			case VARIO_S:					// Vario
				block = new VarioBlock(bytes);
				break;

			case ESC:							// Electronic Speed Control
				block = new EscBlock(bytes);
				break;
				
			case FLIGHT_PACK:			// Flight Battery Capacity (Dual)
				block = new FlightPackBlock(bytes);
				break;

			case RPM:							// rpm, temperature, rx volts, dbm_A, dbm_B
				block = new StandardBlock(bytes, rpmHeaderBlock);
				break;

			case ALT_ZERO:				// Pseudo-device setting Altitude "zero"
				block = new AltitudeZeroBlock(bytes);
				break;

			case RTC:							// Pseudo-device giving timestamp
				block = new RtcBlock(bytes);
				break;

			case FRAMEDATA:				// Transmitter frame data
				block = new FrameDataBlock(bytes);
				break;

			case QOS:							// signal info
				block = new RxBlock(bytes);
				break;
			}
		}
		return block;
	}

	public int getSize() {
		return 20;
	}

	@Override
	public List<Integer> getMeasurementValues() {
		return measurementValues;
	}

	@Override
	public List<String> getMeasurementNames() {
		return measurementNames;
	}

	@Override
	public List<String> getMeasurementUnits() {
		return measurementUnits;
	}

	@Override
	public List<Double> getMeasurementFactors() {
		return measurementFactors;
	}

	/**
	 * generic toString method for all derived BlockData classes excluding GPS related classes
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder().append(String.format("%-18s", this.getClass().getSimpleName())).append(getTimestamp()).append(" - ");
		measurementNames = getMeasurementNames();
		measurementUnits = getMeasurementUnits();
		measurementFactors = getMeasurementFactors();
		measurementValues = getMeasurementValues();
		for(int i=0; i < measurementNames.size(); ++i) {
			sb.append(measurementNames.get(i))
			.append(" = ")
			.append(measurementFactors.get(i) != 1.0 
					? String.format("%.1f", (measurementValues.get(i) * measurementFactors.get(i)))
					: String.format("%d", (int)(measurementValues.get(i) * measurementFactors.get(i))))
			.append(" ")
			.append(measurementUnits.get(i))
			.append("; ");
		}
		return sb.toString();
	}

	/**
	 * generic method for all derived BlockData classes to be used as CSV export header line
	 */
	public String getNamesAndUnitsAsCCV() {
		StringBuilder sb = new StringBuilder().append(String.format("%-18s, Time [ms],", this.getClass().getSimpleName()));
		measurementNames = getMeasurementNames();
		measurementUnits = getMeasurementUnits();
		for(int i=0; i < measurementNames.size(); ++i) {
			sb.append(measurementNames.get(i)).append(" [").append(measurementUnits.get(i)).append("],");
		}
		return sb.toString();
	}

	/**
	 * generic method for all derived BlockData classes to be used as CSV export data line
	 */
	public String getCorrectedValuesAsCCV() {
		StringBuilder sb = new StringBuilder().append(String.format("%-18s, %d,", this.getClass().getSimpleName(), getTimestamp() * 10));
		measurementValues = getMeasurementValues();
		measurementFactors = getMeasurementFactors();
		for(int i=0; i < measurementValues.size(); ++i) {
			sb.append(String.format(" %7.2f,", measurementValues.get(i) * measurementFactors.get(i)));
		}
		return sb.toString();
	}
}
