package com.monstarmike.tlmreader.datablock;

import java.util.Arrays;

public class HeaderNameBlock extends HeaderBlock {
	String modelName = null;
	int modelNumber = -1;
	String modelType = null;
	String bindInfo = null;

	public static boolean isHeaderName(byte[] bytes) {
		return bytes.length > 6 && bytes[4] != bytes[5];
	}

	public String get_modelName() {
		if (this.modelName == null) {
			this.modelName = new String(Arrays.copyOfRange(this.rawData, 0x0C,
					0x16)).trim();
		}

		return this.modelName;
	}

	public int get_modelNumber() {
		if (this.modelNumber < 0) {
			this.modelNumber = ((short) this.rawData[0x4]) + 1;
		}

		return this.modelNumber;
	}

	public String get_modelType() {
		if (this.modelType == null) {
			switch (this.rawData[0x5]) {
			case 0x0:
				this.modelType = "Fixed Wing";
				break;

			case 0x1:
				this.modelType = "Helicopter";
				break;

			case 0x2:
				this.modelType = "Sailplane";
				break;
			}
		}

		return this.modelType;
	}

	public String get_bindInfo() {
		if (this.bindInfo == null) {
			switch (this.rawData[0x6]) {
			case 0x1:
				this.bindInfo = "DSM2 6000";
				break;

			case 0x2:
				this.bindInfo = "DSM2 8000";
				break;

			case 0x3:
				this.bindInfo = "DSMX 8000";
				break;

			case 0x4:
				this.bindInfo = "DSMX 6000";
				break;

			}
		}

		return this.bindInfo;
	}

	public HeaderNameBlock(byte[] rawData) {
		super(rawData);
	}

}
