package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class GForceBlock extends DataBlock {
	/*
	 * Address 0x14: G-Force
	 * 
	 * 14,00,xMSB,xLSB,yMSB,zMSB,zLSB,xmaxMSB,xmaxLSB,yma
	 * xMSB,ymaxLSB,zmaxMSB,zmaxLSB,zminMSB,zminLSB MSB/LSB are signed integers,
	 * unit is 0.01g, on the "normal" display the last digit is rounded. maximum
	 * display value is +-99.90 (actually 99.99, but when it is rounded, it
	 * overflows to 100 and displays ----) The min/max values are retained in
	 * the sensor, because g-force cah change too quickly for the radio.
	 * Therefore you cannot clear the min/max values on the radio. Alarm can be
	 * set for Z-axis from -40g to 40g
	 */

	Short x = null, y = null, z = null, maxX = null, maxY = null, maxZ = null,
			minZ = null;

	public short get_x() {
		if (this.x == null) {
			this.x = Shorts.fromBytes(this.rawData[6], this.rawData[7]);
		}
		return this.x;
	}

	public short get_y() {
		if (this.y == null) {
			this.y = Shorts.fromBytes(this.rawData[8], this.rawData[9]);
		}
		return this.y;
	}

	public short get_z() {
		if (this.z == null) {
			this.z = Shorts.fromBytes(this.rawData[10], this.rawData[11]);
		}
		return this.z;
	}

	public short get_maxX() {
		if (this.maxX == null) {
			this.maxX = Shorts.fromBytes(this.rawData[12], this.rawData[13]);
		}
		return this.maxX;
	}

	public short get_maxY() {
		if (this.maxY == null) {
			this.maxY = Shorts.fromBytes(this.rawData[14], this.rawData[15]);
		}
		return this.maxY;
	}

	public short get_maxZ() {
		if (this.maxZ == null) {
			this.maxZ = Shorts.fromBytes(this.rawData[16], this.rawData[17]);
		}
		return this.maxZ;
	}

	public short get_minZ() {
		if (this.minZ == null) {
			this.minZ = Shorts.fromBytes(this.rawData[18], this.rawData[19]);
		}
		return this.minZ;
	}

	public GForceBlock(byte[] rawData) {
		super(rawData);
	}
}
