package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class StandardBlock extends DataBlock {

	Double rpm;
	Double volt;
	Short tempInGradFahrenheit;
	private Double ratio = 1.0;
	private Byte poles = 1;

	public double get_rpm() {
		if (this.rpm == null) {
			short rawRpmData = Shorts.fromBytes(this.rawData[6], this.rawData[7]);
			if (rawRpmData == 0) { // 0x0000
				this.rpm = 0.0;
			} else {
				this.rpm = (1.0 / rawRpmData * 120000000.0) / ratio / poles;
			}
		}
		return this.rpm;
	}

	public double get_volt() {
		if (this.volt == null) {
			this.volt = (double) Shorts.fromBytes(this.rawData[8], this.rawData[9]) / 100.0;
		}
		return this.volt;
	}

	public double get_temperature() {
		return get_temperatureInGradCelsius();
	}

	public short get_temperatureInGradFahrenheit() {
		if (this.tempInGradFahrenheit == null) {
			this.tempInGradFahrenheit = Shorts.fromBytes(this.rawData[10], this.rawData[11]);
		}
		return this.tempInGradFahrenheit;
	}

	public double get_temperatureInGradCelsius() {
		get_temperatureInGradFahrenheit();
		return ((double)this.tempInGradFahrenheit - 32) / 1.8;
	}

	public StandardBlock(byte[] rawData, HeaderRpmBlock rpmHeader) {
		super(rawData);
		if (rpmHeader != null) {
			ratio = rpmHeader.getRatio();
			poles = rpmHeader.getPoles();
		}
	}

	@Override
	public String toString() {
		return super.toString() + "StandardData; RPM: " + this.get_rpm() + ", Volt: " + this.get_volt()
				+ ", Temperature (in °F): " + this.get_temperatureInGradFahrenheit() + ", Temperature (in °C): "
				+ get_temperatureInGradCelsius();
	}
}
