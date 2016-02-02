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

	private float x, y, z, maxX, maxY, maxZ, minZ;

	public float get_x() {
		return x;
	}

	public float get_y() {
		return y;
	}

	public float get_z() {
		return z;
	}

	public float get_maxX() {
		return maxX;
	}

	public float get_maxY() {
		return maxY;
	}

	public float get_maxZ() {
		return maxZ;
	}

	public float get_minZ() {
		return minZ;
	}

	public GForceBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		x = 0.01f * Shorts.fromBytes(rawData[6], rawData[7]);
		y = 0.01f * Shorts.fromBytes(rawData[8], rawData[9]);
		z = 0.01f * Shorts.fromBytes(rawData[10], rawData[11]);
		maxX = 0.01f * Shorts.fromBytes(rawData[12], rawData[13]);
		maxY = 0.01f * Shorts.fromBytes(rawData[14], rawData[15]);
		maxZ = 0.01f * Shorts.fromBytes(rawData[16], rawData[17]);
		minZ = 0.01f * Shorts.fromBytes(rawData[18], rawData[19]);
	}
}
