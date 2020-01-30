package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;

public class StandardBlock extends DataBlock {
	
//typedef struct
//{
//	UINT8		identifier;										// Source device = 0x7E
//	UINT8		sID;													// Secondary ID
//	UINT16	microseconds;									// microseconds between pulse leading edges
//	UINT16	volts;												// 0.01V increments (typically flight pack voltage)
//	INT16		temperature;									// Temperature in degrees F.  0x7FFF = "No Data"
//	INT8		dBm_A,												// Avg RSSI in dBm (<-1 = dBm, 0 = no data, >0 = % range) -- (legacy)antenna A in dBm
//					dBm_B;												// Avg RSSI in % (<-1 = dBm, 0 = no data, >0 = % range)   -- (legacy)antenna B in dBm
//																				// Note: Legacy use as antenna A/B dBm values is still supported. If only 1 antenna, set B = A.
//																				// The "no data" id is 0, but -1 (0xFF) is treated the same for backwards compatibility
//	UINT16		spare[2];
//} STRU_TELE_RPM;

	private float rpm;
	private short voltageInHunderthOfVolts;
	private short tempInDegreeFahrenheit;
	private short ratioInHunderth = 100;
	private byte poles = 1;
	private int rawRpmData;
	private int dbm_A;
	private int dbm_B;

	public StandardBlock(final byte[] rawData, final HeaderRpmBlock rpmHeader) {
		super(rawData);
		if (rpmHeader != null) {
			ratioInHunderth = rpmHeader.getRatioInHunderth();
			poles = rpmHeader.getPoles();
		}
		decode(rawData);
		
		measurementNames.add("RPM St");
		measurementNames.add("Volt St");
		measurementNames.add("Temperature St");
		measurementNames.add("dbm_A");
		measurementNames.add("dbm_B");

		measurementUnits.add("1/min");
		measurementUnits.add("V");
		measurementUnits.add("°C");
		measurementUnits.add("dBm");
		measurementUnits.add("dBm");

		measurementFactors.add(1.0);
		measurementFactors.add(0.01);
		measurementFactors.add(0.1);
		measurementFactors.add(1.0);
		measurementFactors.add(1.0);
	}
	
	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof StandardBlock) {
			StandardBlock rpm = (StandardBlock) block;
			return rpm.rawRpmData == rawRpmData
					&& rpm.voltageInHunderthOfVolts == voltageInHunderthOfVolts
					&& rpm.tempInDegreeFahrenheit == tempInDegreeFahrenheit
					&& rpm.dbm_A == dbm_A
					&& rpm.dbm_B == dbm_B;
		}
		return false;
	}

	public boolean hasValidRpmData() {
		return rawRpmData != 0xFFFF && rawRpmData != 0x0000;
	}

	public float getRpm() {
		return hasValidRpmData() ? rpm : 0.0f;
	}

	public boolean hasValidVoltageData() {
		return voltageInHunderthOfVolts != Short.MIN_VALUE && voltageInHunderthOfVolts != 0x0000;
	}

	public short getVoltageInHunderthOfVolts() {
		return voltageInHunderthOfVolts;
	}

	public boolean hasValidTemperatureData() {
		return tempInDegreeFahrenheit != Short.MIN_VALUE && tempInDegreeFahrenheit != 0x0000;
	}

	public short getTemperatureInDegreeFahrenheit() {
		return tempInDegreeFahrenheit;
	}

	public float getTemperatureInThenthOfDegreeCelsius() {
		return hasValidTemperatureData() ? (tempInDegreeFahrenheit - 32) / 1.8f * 10 : 0.0f;
	}

	/**
	 * @return the dbm_A
	 */
	public int getDbm_A() {
		return dbm_A;
	}

	/**
	 * @return the dbm_B
	 */
	public int getDbm_B() {
		return dbm_B;
	}

	private void decode(final byte[] rawData) {
		rawRpmData = Ints.fromBytes((byte) 0, (byte) 0, rawData[6], rawData[7]);
		if (rawRpmData == 0) { // 0x0000
			rpm = 0.0f;
		} else {
			rpm = (1.0f / rawRpmData * 120000000.0f) / ratioInHunderth * 100 / poles;
		}
		voltageInHunderthOfVolts = Shorts.fromBytes(rawData[8], rawData[9]);
		tempInDegreeFahrenheit = Shorts.fromBytes(rawData[10], rawData[11]);
		dbm_A = rawData[12];
		dbm_B = rawData[13];
		
		measurementValues.add((int)getRpm());
		measurementValues.add((int)getVoltageInHunderthOfVolts());
		measurementValues.add((int)getTemperatureInThenthOfDegreeCelsius());
		measurementValues.add((int)getDbm_A());
		measurementValues.add((int)getDbm_B());
	}

}
