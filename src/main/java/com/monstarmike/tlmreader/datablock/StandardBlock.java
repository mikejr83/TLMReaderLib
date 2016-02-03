package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class StandardBlock extends DataBlock {

	private float rpm, volt;
	private short tempInGradFahrenheit;
	private float ratio = 1.0f;
	private byte poles = 1;

	public float get_rpm() {
		return rpm;
	}

	public float get_volt() {
		return volt;
	}

	public short get_temperatureInGradFahrenheit() {
		return tempInGradFahrenheit;
	}

	public float get_temperatureInGradCelsius() {
		return (tempInGradFahrenheit - 32) / 1.8f;
	}

	public StandardBlock(final byte[] rawData, final HeaderRpmBlock rpmHeader) {
		super(rawData);
		if (rpmHeader != null) {
			ratio = rpmHeader.getRatio();
			poles = rpmHeader.getPoles();
		}
		decode(rawData);
	}

	private void decode(final byte[] rawData) {
		final short rawRpmData = Shorts.fromBytes(rawData[6], rawData[7]);
		if (rawRpmData == 0) { // 0x0000
			rpm = 0.0f;
		} else {
			rpm = (1.0f / rawRpmData * 120000000.0f) / ratio / poles;
		}
		volt = Shorts.fromBytes(rawData[8], rawData[9]) / 100.0f;
		tempInGradFahrenheit = Shorts.fromBytes(rawData[10], rawData[11]);
	}

	@Override
	public String toString() {
		return super.toString() + "StandardData; RPM: " + get_rpm() + ", Volt: " + get_volt()
				+ ", Temperature (in °F): " + get_temperatureInGradFahrenheit() + ", Temperature (in °C): "
				+ get_temperatureInGradCelsius();
	}
}
