package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class HeaderVoltBlock extends HeaderBlock {

	private boolean active;
	private float minVolt;
	private float maxVolt;
	private byte statusReport;
	private byte warningReport;

	public HeaderVoltBlock(byte[] rawData) {
		super(rawData);
		active = new Boolean((byte) 0x01 == rawData[7]);
		minVolt = ((float) Shorts.fromBytes(rawData[13], rawData[12]) * 0.01f);
		maxVolt = ((float) Shorts.fromBytes(rawData[15], rawData[14]) * 0.01f);
		statusReport = new Byte(rawData[16]);
		warningReport = new Byte(rawData[17]);
	}

	public static boolean isVoltHeader(byte[] bytes) {
		return bytes.length > 6 && bytes[4] == (byte) 0x01 && bytes[5] == (byte) 0x01;
	}

	@Override
	public String toString() {
		return "VoltHeader; active: " + isActive() + ", minVolt: " + getMinVolt() + ", maxVolt: " + getMaxVolt()
				+ ", statusReport: " + getStatusReport() + ", warningReport: " + getWarningReport();
	}

	public boolean isActive() {
		return active;
	}

	public float getMinVolt() {
		return minVolt;
	}

	public float getMaxVolt() {
		return maxVolt;
	}

	public String getStatusReport() {
		return convertReportTime(statusReport);
	}

	public String getWarningReport() {
		return convertReportTime(warningReport);
	}
}
