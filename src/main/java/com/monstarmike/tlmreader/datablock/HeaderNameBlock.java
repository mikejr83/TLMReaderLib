package com.monstarmike.tlmreader.datablock;

import java.util.Arrays;

public class HeaderNameBlock extends HeaderBlock {
	String modelName;
	byte modelNumber;
	byte modelType;
	byte bindInfo;

	public static boolean isHeaderName(byte[] bytes) {
		return bytes.length > 6 && bytes[4] != bytes[5];
	}

	public String get_modelName() {
		return modelName;
	}

	public int get_modelNumber() {
		return modelNumber;
	}

	public String get_modelType() {
		switch (modelType) {
		case 0x0:
			return "Fixed Wing";
		case 0x1:
			return "Helicopter";
		default:
		case 0x2:
			return "Sailplane";
		}
	}

	public String get_bindInfo() {
		switch (bindInfo) {
		case 0x1:
			return "DSM2 6000";
		case 0x2:
			return "DSM2 8000";
		case 0x3:
			return "DSMX 8000";
		default:
		case 0x4:
			return "DSMX 6000";
		}
	}

	public HeaderNameBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		modelType = rawData[0x5];
		modelType = rawData[0x5];
		bindInfo = rawData[0x6];
		short offset = 0x0C;
		while (offset < 0x24 && rawData[offset] != 0x0) {
			offset++;
		}
		modelName = new String(Arrays.copyOfRange(rawData, 0x0C, offset)).trim();
		modelNumber = (byte) (((byte) rawData[0x4]) + 1);
	}

	@Override
	public String toString() {
		return "NameHeader; modelName: " + get_modelName() + ", modelNumber: " + get_modelNumber() + ", modelType: "
				+ get_modelType() + ", bindInfo: " + get_bindInfo();
	}

}
