package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class VarioBlock extends DataBlock {

	private float altitude, interval250, interval500, interval1000, interval2000, interval3000;

	public float get_altitude() {
		return altitude;
	}

	public float get_250ms() {
		return interval250;
	}

	public float get_500ms() {
		return interval500;
	}

	public float get_1000ms() {
		return interval1000;
	}

	public float get_2000ms() {
		return interval2000;
	}

	public float get_3000ms() {
		return interval3000;
	}

	public VarioBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		altitude = 0.1f * Shorts.fromBytes(rawData[0x06], rawData[0x07]);
		interval250 = 0.1f * Shorts.fromBytes(rawData[0x08], rawData[0x09]);
		interval500 = 0.1f * Shorts.fromBytes(rawData[0x0A], rawData[0x0B]);
		interval1000 = 0.1f * Shorts.fromBytes(rawData[0x0C], rawData[0x0D]);
		interval2000 = 0.1f * Shorts.fromBytes(rawData[0x0E], rawData[0x0F]);
		interval3000 = 0.1f * Shorts.fromBytes(rawData[0x10], rawData[0x11]);
	}

	@Override
	public String toString() {
		return super.toString() + " - altitude: " + get_altitude() + " - 250 delta: " + get_250ms() + " - 500 delta: "
				+ get_500ms() + " - 1000 delta: " + get_1000ms() + " - 2000 delta: " + get_2000ms() + " - 3000 delta: "
				+ get_3000ms();
	}
}
