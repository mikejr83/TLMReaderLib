package com.monstarmike.tlmreader.datablock;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.common.primitives.Shorts;

public class HeaderVoltBlock extends HeaderBlock {

	Boolean active;
	BigDecimal minVolt;
	BigDecimal maxVolt;
	Byte statusReport;
	Byte warningReport;

	public HeaderVoltBlock(byte[] rawData) {
		super(rawData);
	}

	public static boolean isVoltHeader(byte[] bytes) {
		return bytes.length > 6 && bytes[4] == (byte) 0x01 && bytes[5] == (byte) 0x01;
	}

	@Override
	public String toString() {
		return "VoltHeader; active: " + isActive() + ", minVolt: " + getMinVolt() + ", maxVolt: " + getMaxVolt()
				+ ", statusReport: " + getStatusReport() + ", warningReport: " + getWarningReport();
	}

	public Boolean isActive() {
		if (active == null) {
			active = new Boolean((byte) 0x01 == rawData[7]);
		}
		return active;
	}

	public BigDecimal getMinVolt() {
		if (minVolt == null) {
			minVolt = new BigDecimal((double) Shorts.fromBytes(rawData[13], rawData[12]) / 100).setScale(2,
					RoundingMode.CEILING);
		}
		return minVolt;
	}

	public BigDecimal getMaxVolt() {
		if (maxVolt == null) {
			maxVolt = new BigDecimal((double) Shorts.fromBytes(rawData[15], rawData[14]) / 100).setScale(2,
					RoundingMode.CEILING);
		}
		return maxVolt;
	}

	public String getStatusReport() {
		if (statusReport == null) {
			statusReport = new Byte(rawData[16]);
		}
		return convertReportTime(statusReport);
	}

	public String getWarningReport() {
		if (warningReport == null) {
			warningReport = new Byte(rawData[17]);
		}
		return convertReportTime(warningReport);
	}
}
