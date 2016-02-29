package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class AirspeedBlock extends DataBlock {

	/*
	 * Address 0x11: Airspeed
	 * 
	 * 11.00.MSB,LSB,01,F9,00,00,00,00,00,00,00,00,00,00 MSB and LSB are a 16Bit
	 * value, 1 unit is 1 km/h. Displays up to 65535 km/h, maximum alarm value
	 * 563 km/h
	 */

	private short airspeedKmPerHour;

	public AirspeedBlock(final byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	public short getAirspeedInKmPerHour() {
		return airspeedKmPerHour;
	}

	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof AirspeedBlock) {
			AirspeedBlock air = (AirspeedBlock) block;
			return air.getAirspeedInKmPerHour() == this.airspeedKmPerHour;
		}
		return false;
	}

	private void decode(byte[] rawData) {
		airspeedKmPerHour = Shorts.fromBytes(rawData[6], rawData[7]);
	}
}
