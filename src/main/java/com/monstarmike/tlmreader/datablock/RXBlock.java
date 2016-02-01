package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class RXBlock extends DataBlock {

	short a, b, l, r, frameLoss, holds;
	float volts;

	public RXBlock(final byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(final byte[] rawData) {
		a = Shorts.fromBytes(rawData[0x06], rawData[0x07]);
		b = Shorts.fromBytes(rawData[0x08], rawData[0x09]);
		l = Shorts.fromBytes(rawData[0x0A], rawData[0x0B]);
		r = Shorts.fromBytes(rawData[0x0C], rawData[0x0D]);
		frameLoss = Shorts.fromBytes(rawData[0x0E], rawData[0x0F]);
		holds = Shorts.fromBytes(rawData[0x10], rawData[0x11]);
		volts = (float) ((double) Shorts.fromBytes(rawData[0x12], rawData[0x13]) / 100);
	}

	public Short get_a() {
		return a;
	}

	public Short get_b() {
		return b;
	}

	public Short get_l() {
		return l;
	}

	public Short get_r() {
		return r;
	}

	public Short get_frameLoss() {
		return frameLoss;
	}

	public Short get_holds() {
		return holds;
	}

	public float get_volts() {
		return volts;
	}

	@Override
	public String toString() {
		return super.toString() + "RxData; FrameLoss: " + get_frameLoss() + ", Holds: " + get_holds() + ", Volts: "
				+ get_volts();
	}

}
