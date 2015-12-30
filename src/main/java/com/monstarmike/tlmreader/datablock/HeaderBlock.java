package com.monstarmike.tlmreader.datablock;

public abstract class HeaderBlock extends Block {

	public static boolean isHeaderBlock(byte[] bytes) {
		return bytes[0] == bytes[1] && bytes[1] == bytes[2]
				&& bytes[2] == bytes[3] && bytes[0] == -1;
	}

	@Override
	public int getSize() {
		return 36;
	}

	public HeaderBlock(byte[] rawData) {
		super(rawData);
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

//	@Override
//	public String toString() {
//
//		String modelName = new String(Arrays.copyOfRange(this.rawData, 0x0C,
//				0x23)).trim();
//		// TODO Auto-generated method stub
//		if (this.rawData[4] == 0)
//			return "name info";
//		else
//			return super.toString();
//	}
}
