package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class StandardBlock extends DataBlock {

	Short rpm = null, volt = null, temp = null;

	public Short get_rpm() {
		if (this.rpm == null) {
			this.rpm = Shorts.fromBytes(this.rawData[6], this.rawData[7]);
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

	public StandardBlock(byte[] rawData) {
		super(rawData);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + " - RPM: " + this.get_rpm() + " - Volt: "
				+ this.get_volt() + " - Temperature: " + this.get_temperature();
	}
}
