package com.monstarmike.tlmreader.datablock;

import java.util.Arrays;

import com.monstarmike.tlmreader.util.PrimitiveUtils;

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

	Short x = null, y = null, z = null, maxX = null, maxY = null, maxZ = null;

	public short get_x() {
		if (this.x == null) {
			this.x = new Short(PrimitiveUtils.toShort(Arrays.copyOfRange(
					this.rawData, 6, 8)));
		}
		return this.x;
	}
	
	public short get_y() {
		if (this.y == null) {
			this.y = new Short(PrimitiveUtils.toShort(Arrays.copyOfRange(
					this.rawData, 8, 10)));
		}
		return this.y;
	}
	
	public short get_z() {
		if (this.z == null) {
			this.z = new Short(PrimitiveUtils.toShort(Arrays.copyOfRange(
					this.rawData, 10, 12)));
		}
		return this.z;
	}
	
	public short get_maxX() {
		if (this.maxX == null) {
			this.maxX = new Short(PrimitiveUtils.toShort(Arrays.copyOfRange(
					this.rawData, 12, 14)));
		}
		return this.maxX;
	}
	
	public short get_maxY() {
		if (this.maxY == null) {
			this.maxY = new Short(PrimitiveUtils.toShort(Arrays.copyOfRange(
					this.rawData, 14, 16)));
		}
		return this.maxY;
	}
	
	public short get_maxZ() {
		if (this.maxZ == null) {
			this.maxZ = new Short(PrimitiveUtils.toShort(Arrays.copyOfRange(
					this.rawData, 16, 18)));
		}
		return this.maxZ;
	}

	public GForceBlock(byte[] rawData) {
		super(rawData);
	}
}
