package com.monstarmike.tlmreader.datablock;

import java.util.Arrays;

public class HeaderNameBlock extends HeaderBlock {

	public static boolean isHeaderName(byte[] bytes) {
		return bytes.length > 6 && bytes[4] != bytes[5];
	}

	public String get_modelName() {
		return new String(Arrays.copyOfRange(this.rawData, 0x0C, 0x23)).trim();
	}

	public HeaderNameBlock(byte[] rawData) {
		super(rawData);
	}

}
