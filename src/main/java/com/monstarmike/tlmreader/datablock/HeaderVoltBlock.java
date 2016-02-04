package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class HeaderVoltBlock extends HeaderBlock {

	private boolean active;
	private short minVoltageInHunderthOfVolts;
	private short maxVoltageInHunderthOfVolts;
	private byte statusReport;
	private byte warningReport;

	public HeaderVoltBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		active = new Boolean((byte) 0x01 == rawData[7]);
		minVoltageInHunderthOfVolts = Shorts.fromBytes(rawData[13], rawData[12]);
		maxVoltageInHunderthOfVolts = Shorts.fromBytes(rawData[15], rawData[14]);
		statusReport = new Byte(rawData[16]);
		warningReport = new Byte(rawData[17]);
	}

	public static boolean isVoltHeader(byte[] bytes) {
		return bytes.length > 6 && bytes[4] == (byte) 0x01 && bytes[5] == (byte) 0x01;
	}

	@Override
	public String toString() {
		return "VoltHeader; active: " + isActive() + ", minVolt: " + getMinVoltageInHunderthOfVolts() + ", maxVolt: " + getMaxVoltageInHunderthOfVolts()
				+ ", statusReport: " + getStatusReport() + ", warningReport: " + getWarningReport();
	}

	public boolean isActive() {
		return active;
	}

	public short getMinVoltageInHunderthOfVolts() {
		return minVoltageInHunderthOfVolts;
	}

	public short getMaxVoltageInHunderthOfVolts() {
		return maxVoltageInHunderthOfVolts;
	}

	public String getStatusReport() {
		return convertReportTime(statusReport);
	}

	public String getWarningReport() {
		return convertReportTime(warningReport);
	}
}
