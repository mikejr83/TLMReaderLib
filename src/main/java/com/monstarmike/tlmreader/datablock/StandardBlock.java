package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class StandardBlock extends DataBlock {

	Short rpm = null, volt = null, temp = null;
	private Double ratio;
	private Byte poles;

	public Short get_rpm() {
		if (this.rpm == null) {
			this.rpm = (short) ((1.0 / Shorts.fromBytes(this.rawData[6], this.rawData[7]) * 120000000.0) / ratio
					/ poles);
		}
		return this.rpm;
	}

	public Short get_volt() {
		if (this.volt == null) {
			this.volt = Shorts.fromBytes(this.rawData[8], this.rawData[9]);
		}
		return this.volt;
	}

	public Short get_temperature() {
		if (this.temp == null) {
			this.temp = Shorts.fromBytes(this.rawData[10], this.rawData[11]);
		}
		return this.temp;
	}

	public StandardBlock(byte[] rawData, HeaderRpmBlock rpmHeader) {
		super(rawData);
		ratio = rpmHeader.getRatio();
		poles = rpmHeader.getPoles();
	}

	@Override
	public String toString() {
		return super.toString() + " - RPM: " + this.get_rpm() + " - Volt: " + this.get_volt() + " - Temperature: "
				+ this.get_temperature();
	}
}
