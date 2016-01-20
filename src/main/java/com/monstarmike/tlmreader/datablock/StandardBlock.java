package com.monstarmike.tlmreader.datablock;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.common.primitives.Shorts;

public class StandardBlock extends DataBlock {

	BigDecimal rpm;
	BigDecimal volt;
	BigDecimal tempInGradFahrenheit;
	private Double ratio;
	private Byte poles;

	public BigDecimal get_rpm() {
		if (this.rpm == null) {
			short rawRpmData = Shorts.fromBytes(this.rawData[6], this.rawData[7]);
			if (rawRpmData == -1) { // 0xFFFF
				this.rpm = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
			} else {
				this.rpm = new BigDecimal((1.0 / rawRpmData * 120000000.0) / ratio / poles).setScale(2,
						RoundingMode.HALF_UP);
			}
		}
		return this.rpm;
	}

	public BigDecimal get_volt() {
		if (this.volt == null) {
			this.volt = new BigDecimal(Shorts.fromBytes(this.rawData[8], this.rawData[9]) / 100).setScale(2,
					RoundingMode.HALF_UP);
		}
		return this.volt;
	}

	public BigDecimal get_temperature() {
		return get_temperatureInGradCelsius();
	}

	public BigDecimal get_temperatureInGradFahrenheit() {
		if (this.tempInGradFahrenheit == null) {
			this.tempInGradFahrenheit = new BigDecimal(Shorts.fromBytes(this.rawData[10], this.rawData[11])).setScale(2,
					RoundingMode.HALF_UP);
		}
		return this.tempInGradFahrenheit;
	}

	public BigDecimal get_temperatureInGradCelsius() {
		if (this.tempInGradFahrenheit == null) {
			get_temperatureInGradFahrenheit();
		}
		return new BigDecimal((this.tempInGradFahrenheit.doubleValue() - 32) / 1.8).setScale(2, RoundingMode.HALF_UP);
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
