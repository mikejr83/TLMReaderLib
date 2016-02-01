package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class RXBlock extends DataBlock {

	short a, b, l, r, frameLoss, holds;
	float volts;

	public RXBlock(byte[] rawData) {
		super(rawData);
		decode();
	}

	private void decode() {
		this.a = Shorts.fromBytes(this.rawData[0x06], this.rawData[0x07]);
		this.b = Shorts.fromBytes(this.rawData[0x08], this.rawData[0x09]);
		this.l = Shorts.fromBytes(this.rawData[0x0A], this.rawData[0x0B]);
		this.r = Shorts.fromBytes(this.rawData[0x0C], this.rawData[0x0D]);
		this.frameLoss = Shorts.fromBytes(this.rawData[0x0E], this.rawData[0x0F]);
		this.holds = Shorts.fromBytes(this.rawData[0x10], this.rawData[0x11]);
		this.volts = (float) ((double) Shorts.fromBytes(this.rawData[0x12], this.rawData[0x13]) / 100);
	}

	public Short get_a() {
		return this.a;
	}

	public Short get_b() {
		return this.b;
	}

	public Short get_l() {
		return this.l;
	}

	public Short get_r() {
		return this.r;
	}

	public Short get_frameLoss() {
		return this.frameLoss;
	}

	public Short get_holds() {
		return this.holds;
	}

	public float get_volts() {
		return this.volts;
	}

	@Override
	public String toString() {
		return super.toString() + "RxData; FrameLoss: " + get_frameLoss() + ", Holds: " + get_holds() + ", Volts: "
				+ get_volts();
	}

}
