package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class HeaderDataBlock extends HeaderBlock {
	boolean terminatingBlock = false;
	private short sensorType;

	public String getSensorTypeEnabled() {
		byte[] sensorTypeBytes = Shorts.toByteArray(sensorType);
		if (sensorTypeBytes[0x00] != sensorTypeBytes[0x01]) {
			return "invalid information! 0x4 does not equal 0x5";
		}
		switch (sensorTypeBytes[0x00]) {
		case 0x1:
			return "Volts";
		case 0x2:
			return "Temperature";
		case 0x3:
			return "Amps";
		case 0x0A:
			return "PowerBox";
		case 0x11:
			return "Speed";
		case 0x12:
			return "Altimeter";
		case 0x14:
			return "G-Force";
		case 0x15:
			return "JetCat";
		case 0x16:
			return "GPS";
		case 0x17:
			this.terminatingBlock = true;
			return "Terminating Block";
		default:
			return "Unknown Header Block";
		}
	}

	public boolean isTerminatingBlock() {
		return terminatingBlock;
	}

	public HeaderDataBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		sensorType = Shorts.fromBytes(rawData[4], rawData[5]);
	}

	@Override
	public String toString() {
		return "DataHeader; type: " + getSensorTypeEnabled();
	}

}
