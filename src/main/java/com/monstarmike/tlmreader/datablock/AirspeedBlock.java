package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class AirspeedBlock extends DataBlock {

//	typedef struct
//	{
//		UINT8		identifier;
//		UINT8		sID;															// Secondary ID
//		UINT16	airspeed;													// 1 km/h increments
//		UINT16	maxAirspeed;											// 1 km/h increments
//	} STRU_TELE_SPEED;
	/*
	 * Address 0x11: Airspeed
	 * 
	 * 11.00.MSB,LSB,01,F9,00,00,00,00,00,00,00,00,00,00 MSB and LSB are a 16Bit
	 * id, 1 unit is 1 km/h. Displays up to 65535 km/h, maximum alarm id
	 * 563 km/h
	 */

	private short 				airspeedKmPerHour;

	public AirspeedBlock(final byte[] rawData) {
		super(rawData);
		decode(rawData);
		measurementNames.add("AirSpeed");
		measurementUnits.add("km/h");
		measurementFactors.add(1.0);
	}

	public short getAirspeedInKmPerHour() {
		return airspeedKmPerHour;
	}

	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof AirspeedBlock) {
			AirspeedBlock air = (AirspeedBlock) block;
			return air.airspeedKmPerHour == this.airspeedKmPerHour;
		}
		return false;
	}

	private void decode(byte[] rawData) {
		airspeedKmPerHour = Shorts.fromBytes(rawData[6], rawData[7]);
		measurementValues.add((int)getAirspeedInKmPerHour());
	}

}
