package com.monstarmike.tlmreader.datablock;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.common.primitives.Shorts;

public class RXBlock extends DataBlock {

	Short a, b, l, r, frameLoss, holds;
	BigDecimal volts;

	public Short get_a() {
		if (this.a == null) {
			this.a = Shorts.fromBytes(this.rawData[0x06], this.rawData[0x07]);
		}
		return this.a;
	}

	public Short get_b() {
		if (this.b == null) {
			this.b = Shorts.fromBytes(this.rawData[0x08], this.rawData[0x09]);
		}
		return this.b;
	}

	public Short get_l() {
		if (this.l == null) {
			this.l = Shorts.fromBytes(this.rawData[0x0A], this.rawData[0x0B]);
		}
		return this.l;
	}

	public Short get_r() {
		if (this.r == null) {
			this.r = Shorts.fromBytes(this.rawData[0x0C], this.rawData[0x0D]);
		}
		return this.r;
	}

	public Short get_frameLoss() {
		if (this.frameLoss == null) {
			this.frameLoss = Shorts.fromBytes(this.rawData[0x0E], this.rawData[0x0F]);
		}
		return this.frameLoss;
	}

	public Short get_holds() {
		if (this.holds == null) {
			this.holds = Shorts.fromBytes(this.rawData[0x10], this.rawData[0x11]);
		}
		return this.holds;
	}

	public BigDecimal get_volts() {
		if (this.volts == null) {
			this.volts = new BigDecimal((double) Shorts.fromBytes(this.rawData[0x12], this.rawData[0x13]) / 100)
					.setScale(2, RoundingMode.CEILING);
		}
		return this.volts;
	}

	public RXBlock(byte[] rawData) {
		super(rawData);
	}

	@Override
	public String toString() {
		return super.toString() + "RxData; FrameLoss: " + get_frameLoss() + ", Holds: " + get_holds() + ", Volts: "
				+ get_volts();
	}

}
