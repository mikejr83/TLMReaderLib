package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class VarioBlock extends DataBlock {

	private short altitudeInTenthOfMeter, deltaInIntervalOf250MsInTenthOfMeter, deltaInIntervalOf500MsInTenthOfMeter,
			deltaInIntervalOf1000MsInTenthOfMeter, deltaInIntervalOf2000MsInTenthOfMeter,
			deltaInIntervalOf3000MsInTenthOfMeter;

	public short getAltitudeInTenthOfMeter() {
		return altitudeInTenthOfMeter;
	}

	public short getDeltaInIntervalOf250MsInTenthOfMeter() {
		return deltaInIntervalOf250MsInTenthOfMeter;
	}

	public short getDeltaInIntervalOf500MsInTenthOfMeter() {
		return deltaInIntervalOf500MsInTenthOfMeter;
	}

	public short getDeltaInIntervalOf1000MsInTenthOfMeter() {
		return deltaInIntervalOf1000MsInTenthOfMeter;
	}

	public short getDeltaInIntervalOf2000MsInTenthOfMeter() {
		return deltaInIntervalOf2000MsInTenthOfMeter;
	}

	public short getDeltaInIntervalOf3000MsInTenthOfMeter() {
		return deltaInIntervalOf3000MsInTenthOfMeter;
	}

	public VarioBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		altitudeInTenthOfMeter = Shorts.fromBytes(rawData[0x06], rawData[0x07]);
		deltaInIntervalOf250MsInTenthOfMeter = Shorts.fromBytes(rawData[0x08], rawData[0x09]);
		deltaInIntervalOf500MsInTenthOfMeter = Shorts.fromBytes(rawData[0x0A], rawData[0x0B]);
		deltaInIntervalOf1000MsInTenthOfMeter = Shorts.fromBytes(rawData[0x0C], rawData[0x0D]);
		deltaInIntervalOf2000MsInTenthOfMeter = Shorts.fromBytes(rawData[0x0E], rawData[0x0F]);
		deltaInIntervalOf3000MsInTenthOfMeter = Shorts.fromBytes(rawData[0x10], rawData[0x11]);
	}

	@Override
	public String toString() {
		return super.toString() + " - altitude: " + getAltitudeInTenthOfMeter() + " - 250 delta: " + getDeltaInIntervalOf250MsInTenthOfMeter() + " - 500 delta: "
				+ getDeltaInIntervalOf500MsInTenthOfMeter() + " - 1000 delta: " + getDeltaInIntervalOf1000MsInTenthOfMeter() + " - 2000 delta: " + getDeltaInIntervalOf2000MsInTenthOfMeter() + " - 3000 delta: "
				+ getDeltaInIntervalOf3000MsInTenthOfMeter();
	}
}
