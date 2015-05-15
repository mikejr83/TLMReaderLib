package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class VarioBlock extends DataBlock {

	Short altitude = null, interval250 = null, interval500 = null,
			interval1000 = null, interval2000 = null, interval3000 = null;

	public Short get_altitude() {
		if (this.altitude == null) {
			this.altitude = Shorts.fromBytes(this.rawData[0x06],
					this.rawData[0x07]);
		}
		return this.altitude;
	}

	public Short get_250ms() {
		if (this.interval250 == null) {
			this.interval250 = Shorts.fromBytes(this.rawData[0x08],
					this.rawData[0x09]);
		}
		return this.interval250;
	}

	public Short get_500ms() {
		if (this.interval500 == null) {
			this.interval500 = Shorts.fromBytes(this.rawData[0x0A],
					this.rawData[0x0B]);
		}
		return this.interval500;
	}

	public Short get_1000ms() {
		if (this.interval1000 == null) {
			this.interval1000 = Shorts.fromBytes(this.rawData[0x0C],
					this.rawData[0x0D]);
		}
		return this.interval1000;
	}

	public Short get_2000ms() {
		if (this.interval2000 == null) {
			this.interval2000 = Shorts.fromBytes(this.rawData[0x0E],
					this.rawData[0x0F]);
		}
		return this.interval2000;
	}

	public Short get_3000ms() {
		if (this.interval3000 == null) {
			this.interval3000 = Shorts.fromBytes(this.rawData[0x10],
					this.rawData[0x11]);
		}
		return this.interval3000;
	}

	public VarioBlock(byte[] rawData) {
		super(rawData);
	}

	@Override
	public String toString() {
		return super.toString() + " - altitude: " + this.get_altitude()
				+ " - 250 delta: " + this.get_250ms() + " - 500 delta: "
				+ this.get_500ms() + " - 1000 delta: " + this.get_1000ms()
				+ " - 2000 delta: " + this.get_2000ms() + " - 3000 delta: "
				+ this.get_3000ms();
	}
}
