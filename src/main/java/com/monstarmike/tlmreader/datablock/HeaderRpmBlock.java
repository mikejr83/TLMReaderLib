package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class HeaderRpmBlock extends HeaderBlock {

	byte poles;
	boolean active;
	float ratio;
	short minRpm;
	short maxRpm;
	byte statusReport;
	byte warningReport;

	public HeaderRpmBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		poles = new Byte(rawData[6]);
		active = new Boolean((byte) 0x01 == rawData[7]);
		ratio = (float) Shorts.fromBytes(rawData[11], rawData[10]) * 0.01f;
		minRpm = Shorts.fromBytes(rawData[13], rawData[12]);
		maxRpm = Shorts.fromBytes(rawData[15], rawData[14]);
		statusReport = new Byte(rawData[16]);
		warningReport = new Byte(rawData[17]);
	}

	public static boolean isRpmHeader(byte[] bytes) {
		return bytes.length > 6 && bytes[4] == (byte) 0x7E && bytes[5] == (byte) 0x7E;		
	}

	@Override
	public String toString() {
		return "RpmHeader; poles: " + getPoles() + ", active: " + isActive() + ", ratio: " + getRatio() + ", minRpm: "
				+ getMinRpm() + ", maxRpm: " + getMaxRpm() + ", statusReport: " + getStatusReport()
				+ ", warningReport: " + getWarningReport();
	}

	public byte getPoles() {
		return poles;
	}

	public boolean isActive() {
		return active;
	}

	public float getRatio() {
		return ratio;
	}

	public short getMinRpm() {
		return minRpm;
	}

	public short getMaxRpm() {
		return maxRpm;
	}

	public String getStatusReport() {
		return convertReportTime(statusReport);
	}

	public String getWarningReport() {
		return convertReportTime(warningReport);
	}
}
