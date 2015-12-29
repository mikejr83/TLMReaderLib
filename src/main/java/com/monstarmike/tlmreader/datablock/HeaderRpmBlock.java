package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class HeaderRpmBlock extends HeaderBlock {

	Byte poles;
	Boolean active;
	Double ratio;
	Short minRpm;
	Short maxRpm;
	Byte statusReport;
	Byte warningReport;

	public HeaderRpmBlock(byte[] rawData) {
		super(rawData);
	}

	public static boolean isRpmHeader(byte[] bytes) {
		return bytes.length > 6 && bytes[4] == (byte) 0x7E && bytes[5] == (byte) 0x7E;
	}

	@Override
	public String toString() {
		return "RpmHeader; poles: " + getPoles() + ", isActive: " + isActive() + ", ratio: " + getRatio() + ", minRpm: "
				+ getMinRpm() + ", maxRpm: " + getMaxRpm() + ", statusReport: " + getStatusReport()
				+ ", warningReport: " + getWarningReport();
	}

	public Byte getPoles() {
		if (poles == null) {
			poles = new Byte(rawData[6]);
		}
		return poles;
	}

	public Boolean isActive() {
		if (active == null) {
			active = new Boolean((byte) 0x01 == rawData[7]);
		}
		return active;
	}

	public Double getRatio() {
		if (ratio == null) {
			ratio = (double) Shorts.fromBytes(rawData[11], rawData[10]) / 100;
		}
		return ratio;
	}

	public Short getMinRpm() {
		if (minRpm == null) {
			minRpm = Shorts.fromBytes(rawData[13], rawData[12]);
		}
		return minRpm;
	}

	public Short getMaxRpm() {
		if (maxRpm == null) {
			maxRpm = Shorts.fromBytes(rawData[15], rawData[14]);
		}
		return maxRpm;
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

	private String convertReportTime(Byte report) {
		switch (report) {
		case 1:
			return "5 sec";
		case 2:
			return "10 sec";
		case 3:
			return "15 sec";
		case 4:
			return "20 sec";
		case 5:
			return "30 sec";
		case 6:
			return "45 sec";
		case 7:
			return "60 sec";
		}
		// 0
		return "Inh";
	}
}
