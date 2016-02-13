package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;

public class StandardBlock extends DataBlock {

	private float rpm;
	private short voltageInHunderthOfVolts;
	private short tempInGradFahrenheit;
	private short ratioInHunderth = 100;
	private byte poles = 1;
	private int rawRpmData;

	public boolean hasValidRpmData() {
		return rawRpmData != 0xFFFF && rawRpmData != 0x0000;
	}

	public float getRpm() {
		return rpm;
	}

	public boolean hasValidVoltageData() {
		return voltageInHunderthOfVolts != Short.MIN_VALUE && voltageInHunderthOfVolts != 0x0000;
	}

	public short getVoltageInHunderthOfVolts() {
		return voltageInHunderthOfVolts;
	}

	public boolean hasValidTemperatureData() {
		return tempInGradFahrenheit != Short.MIN_VALUE && tempInGradFahrenheit != 0x0000;
	}

	public short getTemperatureInGradFahrenheit() {
		return tempInGradFahrenheit;
	}

	public float getTemperatureInGradCelsius() {
		return (tempInGradFahrenheit - 32) / 1.8f;
	}

	public StandardBlock(final byte[] rawData, final HeaderRpmBlock rpmHeader) {
		super(rawData);
		if (rpmHeader != null) {
			ratioInHunderth = rpmHeader.getRatioInHunderth();
			poles = rpmHeader.getPoles();
		}
		decode(rawData);
	}

	private void decode(final byte[] rawData) {
		rawRpmData = Ints.fromBytes((byte) 0, (byte) 0, rawData[6], rawData[7]);
		if (rawRpmData == 0) { // 0x0000
			rpm = 0.0f;
		} else {
			rpm = (1.0f / rawRpmData * 120000000.0f) / ratioInHunderth * 100 / poles;
		}
		voltageInHunderthOfVolts = Shorts.fromBytes(rawData[8], rawData[9]);
		tempInGradFahrenheit = Shorts.fromBytes(rawData[10], rawData[11]);
	}

	@Override
	public String toString() {
		return "StandardData; RPM: " + getRpm() + " (" + hasValidRpmData() + ") , Volt: "
				+ getVoltageInHunderthOfVolts() / 100.0 + "(" + hasValidVoltageData() + ") , Temperature (in °F): "
				+ getTemperatureInGradFahrenheit() + "(" + hasValidTemperatureData() + ") , Temperature (in °C): "
				+ getTemperatureInGradCelsius() + "(" + hasValidTemperatureData() + ")";
	}
}
