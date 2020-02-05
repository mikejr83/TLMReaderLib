package com.monstarmike.tlmreader.datablock;

import com.monstarmike.tlmreader.datablock.normalizer.DataNormalizer;
import com.monstarmike.tlmreader.datablock.normalizer.NoneNormalizer;

public abstract class HeaderBlock {

	public static boolean isHeaderBlock(byte[] bytes) {
		return bytes[0] == bytes[1] && bytes[1] == bytes[2]
				&& bytes[2] == bytes[3] && bytes[0] == -1;
	}

	public int getSize() {
		return 36;
	}

	public HeaderBlock(byte[] rawData) {
	}
	
	public DataNormalizer getNormalizer() {
		return new NoneNormalizer();
	}

	protected String convertReportTime(Byte report) {
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
