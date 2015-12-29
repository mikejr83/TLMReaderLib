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

	public BigDecimal getRpm() {
		if (this.rpm == null) {
			short rawRpmData = Shorts.fromBytes(this.rawData[6], this.rawData[7]);
			if (rawRpmData == -1) { // 0xFFFF
				this.rpm = new BigDecimal(0).setScale(2, RoundingMode.CEILING);
			} else {
				this.rpm = new BigDecimal((1.0 / rawRpmData * 120000000.0) / ratio / poles).setScale(2,
						RoundingMode.CEILING);
			}
		}
		return this.rpm;
	}

	public BigDecimal getVolt() {
		if (this.volt == null) {
			this.volt = new BigDecimal(Shorts.fromBytes(this.rawData[8], this.rawData[9]) / 100).setScale(2,
					RoundingMode.CEILING);
		}
		return this.volt;
	}

	public BigDecimal getTemperatureInGradFahrenheit() {
		if (this.tempInGradFahrenheit == null) {
			this.tempInGradFahrenheit = new BigDecimal(Shorts.fromBytes(this.rawData[10], this.rawData[11])).setScale(2,
					RoundingMode.CEILING);
		}
		return this.tempInGradFahrenheit;
	}

	public BigDecimal getTemperatureInGradCelsius() {
		if (this.tempInGradFahrenheit == null) {
			getTemperatureInGradFahrenheit();
		}
		return new BigDecimal((this.tempInGradFahrenheit.doubleValue() - 32) / 1.8).setScale(2, RoundingMode.CEILING);
	}

	public StandardBlock(byte[] rawData, HeaderRpmBlock rpmHeader) {
		super(rawData);
		ratio = rpmHeader.getRatio();
		poles = rpmHeader.getPoles();
	}

	@Override
	public String toString() {
		return super.toString() + " - RPM: " + this.getRpm() + ", Volt: " + this.getVolt() + ", Temperature (in °F): "
				+ this.getTemperatureInGradFahrenheit() + ", Temperature (in °C): " + getTemperatureInGradCelsius();
	}
}
