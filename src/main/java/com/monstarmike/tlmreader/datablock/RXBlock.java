package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;


/**
 * The Values of a, b, l ,r are number packetLoss of the each receiver. 
 * It looks like, that if the value is 0xFFFF or Short.MIN_VALUE there is no such receiver installed.
 */

public class RXBlock extends DataBlock {

	
	private short a, b, l, r, frameLoss, holds;
	private short voltageInHunderthOfVolts;

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
		voltageInHunderthOfVolts = Shorts.fromBytes(rawData[0x12], rawData[0x13]);
	}

	public short getA() {
		return a;
	}

	public short getB() {
		return b;
	}

	public short getL() {
		return l;
	}

	public short getR() {
		return r;
	}

	public short getFrameLoss() {
		return frameLoss;
	}

	public short getHolds() {
		return holds;
	}

	public short getVoltageInHunderthOfVolts() {
		return voltageInHunderthOfVolts;
	}

	@Override
	public String toString() {
		return super.toString() + "RxData; FrameLoss: " + getFrameLoss() + ", Holds: " + getHolds() + ", Volts: "
				+ getVoltageInHunderthOfVolts();
	}

}
