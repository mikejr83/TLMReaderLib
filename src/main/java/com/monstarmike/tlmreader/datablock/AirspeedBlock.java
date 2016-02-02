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

	private short airspeed;;

	public short get_airspeed() {
		return airspeed;
	}

	public AirspeedBlock(final byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		airspeed = Shorts.fromBytes(rawData[6], rawData[7]);
	}
}
