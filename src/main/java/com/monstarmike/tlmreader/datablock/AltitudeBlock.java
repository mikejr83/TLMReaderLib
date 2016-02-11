package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public final class AltitudeBlock extends DataBlock {
	/*
	 * Timestamp takes up the first 4 bytes Address 0x12: Altitude
	 * 
	 * 12.00.MSB,LSB,FF,00,00,00,00,00,00,00,00,00,00,00 MSB and LSB are a 16Bit
	 * signed integer, 1 unit is 0.1m. Displays -3276.8m to 3276.7m, alarm
	 * values between -300m and 1000m. 0x7fff is 3276.7m, 0xffff=-0.1m, 0x8000
	 * is -3276.8m
	 */

	private short altitudeInTenthOfMeter;

	public short getAltitudeInTenthOfMeter() {
		return this.altitudeInTenthOfMeter;
	}

	public AltitudeBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		altitudeInTenthOfMeter = Shorts.fromBytes(rawData[6], rawData[7]);
	}

}
