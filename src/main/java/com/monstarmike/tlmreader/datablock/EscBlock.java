package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Ints;

public class EscBlock extends DataBlock {
	
//Uses big-endian byte order	
//	typedef struct
//	{
//		UINT8		identifier;												// Source device = 0x20
//		UINT8		sID;															// Secondary ID
//		UINT16	RPM;															// Electrical RPM, 10RPM (0-655340 RPM)  0xFFFF --> "No data"
//		UINT16	voltsInput;												// Volts, 0.01v (0-655.34V)       0xFFFF --> "No data"
//		UINT16	tempFET;													// Temperature, 0.1C (0-6553.4C)  0xFFFF --> "No data"
//		UINT16	currentMotor;											// Current, 10mA (0-655.34A)      0xFFFF --> "No data"
//		UINT16	tempBEC;													// Temperature, 0.1C (0-6553.4C)  0xFFFF --> "No data"
//		UINT8		currentBEC;												// BEC Current, 100mA (0-25.4A)   0xFF ----> "No data"
//		UINT8		voltsBEC;													// BEC Volts, 0.05V (0-12.70V)    0xFF ----> "No data"
//		UINT8		throttle;													// 0.5% (0-100%)                  0xFF ----> "No data"
//		UINT8		powerOut;													// Power Output, 0.5% (0-127%)    0xFF ----> "No data"
//	} STRU_TELE_ESC;
	
	private int rpmFactorThen;
	private int voltsInputInHundredthOfVolt;
	private int tempFETInTenthOfDegreeCelsius;
	private int currentMotorInHundredthAmps;
	private int tempBECInTenthOfDegreeCelsius;
	private int	currentBECInTenthOfAmps;
	private int	voltsBECInHundredthOfVolt;
	private int	throttleInPercent;
	private int	powerOutInPercent;

	public EscBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
		
		measurementNames.add("RPM ESC");
		measurementNames.add("Voltage ESC");
		measurementNames.add("TempFET ESC");
		measurementNames.add("Current ESC");
		measurementNames.add("CurrentBEC ESC");
		measurementNames.add("VoltsBEC ESC");
		measurementNames.add("Throttle ESC");
		measurementNames.add("PowerOut ESC");
		measurementNames.add("PowerIn ESC");

		measurementUnits.add("1/min");
		measurementUnits.add("V");
		measurementUnits.add("°C");
		measurementUnits.add("A");
		measurementUnits.add("A");
		measurementUnits.add("V");
		measurementUnits.add("%");
		measurementUnits.add("%");
		measurementUnits.add("W");
		
		measurementFactors.add(10.0);
		measurementFactors.add(0.01);
		measurementFactors.add(0.1);
		measurementFactors.add(0.01);
		measurementFactors.add(0.1);
		measurementFactors.add(0.05);
		measurementFactors.add(0.5);
		measurementFactors.add(0.5);
		measurementFactors.add(0.1);
	}
	
	private void decode(byte[] rawData) {
		rpmFactorThen = Ints.fromBytes((byte)0, (byte)0, rawData[6], rawData[7]);
		voltsInputInHundredthOfVolt = Ints.fromBytes((byte)0, (byte)0, rawData[8], rawData[9]);
		tempFETInTenthOfDegreeCelsius = Ints.fromBytes((byte)0, (byte)0, rawData[10], rawData[11]);
		currentMotorInHundredthAmps = Ints.fromBytes((byte)0, (byte)0, rawData[12], rawData[13]);
		tempBECInTenthOfDegreeCelsius = Ints.fromBytes((byte)0, (byte)0, rawData[14], rawData[15]);
		currentBECInTenthOfAmps = rawData[16];
		voltsBECInHundredthOfVolt = rawData[17];
		throttleInPercent = rawData[18];
		powerOutInPercent = rawData[19];
		
		measurementValues.add((int)getRpmFactorThen());
		measurementValues.add((int)getVoltsInputInHundredthOfVolt());
		measurementValues.add((int)getTempFETInTenthOfDegreeCelsius());
		measurementValues.add((int)getCurrentMotorInHundredthAmps());
		measurementValues.add((int)getCurrentBECInTenthOfAmps());
		measurementValues.add((int)getVoltsBECInHundredthOfVolt());
		measurementValues.add((int)getThrottleInPercent());
		measurementValues.add((int)getPowerOutInPercent());
		measurementValues.add((int)getPowerInTenthsOfW());
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof EscBlock) {
			EscBlock esc = (EscBlock) block;
			return esc.rpmFactorThen == rpmFactorThen
					&& esc.voltsInputInHundredthOfVolt == voltsInputInHundredthOfVolt
					&& esc.tempFETInTenthOfDegreeCelsius == tempFETInTenthOfDegreeCelsius
					&& esc.currentMotorInHundredthAmps == currentMotorInHundredthAmps
					&& esc.tempBECInTenthOfDegreeCelsius == tempBECInTenthOfDegreeCelsius
					&& esc.currentBECInTenthOfAmps == currentBECInTenthOfAmps
					&& esc.voltsBECInHundredthOfVolt == voltsBECInHundredthOfVolt
					&& esc.throttleInPercent == throttleInPercent
					&& esc.powerOutInPercent == powerOutInPercent;
		}
		return false;
	}
	
	/**
	 * @return the rpmFactorThen
	 */
	public int getRpmFactorThen() {
		return rpmFactorThen != 0xFFFF ? rpmFactorThen : 0;
	}

	/**
	 * @return the voltsInputInHundredthOfVolt
	 */
	public int getVoltsInputInHundredthOfVolt() {
		return voltsInputInHundredthOfVolt != 0xFFFF ? voltsInputInHundredthOfVolt : 0;
	}

	/**
	 * @return the tempFETInTenthOfDegreeCelsius
	 */
	public int getTempFETInTenthOfDegreeCelsius() {
		return tempFETInTenthOfDegreeCelsius != 0xFFFF ? tempFETInTenthOfDegreeCelsius : 0;
	}

	/**
	 * @return the currentMotorInHundredthAmps
	 */
	public int getCurrentMotorInHundredthAmps() {
		return currentMotorInHundredthAmps != 0xFFFF ? currentMotorInHundredthAmps : 0;
	}

	/**
	 * @return the tempBECInTenthOfDegreeCelsius
	 */
	public int getTempBECInTenthOfDegreeCelsius() {
		return tempBECInTenthOfDegreeCelsius != 0xFFFF ? tempBECInTenthOfDegreeCelsius : 0;
	}

	/**
	 * @return the currentBECInTenthOfAmps
	 */
	public int getCurrentBECInTenthOfAmps() {
		return currentBECInTenthOfAmps != 0xFF ? currentBECInTenthOfAmps & 0xFF: 0;
	}

	/**
	 * @return the voltsBECInHundredthOfVolt
	 */
	public int getVoltsBECInHundredthOfVolt() {
		return voltsBECInHundredthOfVolt != 0xFF ? voltsBECInHundredthOfVolt & 0xFF : 0;
	}

	/**
	 * @return the throttleInPercent
	 */
	public int getThrottleInPercent() {
		return throttleInPercent != -1 ? throttleInPercent & 0xFF : 0;
	}

	/**
	 * @return the powerOutInPercent
	 */
	public int getPowerOutInPercent() {
		return powerOutInPercent != 0xFF ? powerOutInPercent & 0xFF : 0;
	}

	/**
	 * @return the powerOutInPercent
	 */
	public int getPowerInTenthsOfW() {
		return (int) (getCurrentMotorInHundredthAmps() / 1000.0 * getVoltsInputInHundredthOfVolt());
	}

}
