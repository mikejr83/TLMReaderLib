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
