package com.monstarmike.tlmreader.datablock;

public class HeaderBlock extends Block {

	public static boolean isHeaderBlock(byte[] bytes) {
		
		return bytes[0] == bytes[1] && 
				bytes[1] == bytes[2] && bytes[0] == -1;
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 36;
	}

	public HeaderBlock(byte[] rawData) {
		super(rawData);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(this.rawData[5] == 0)
			return "name info";
		else
		return super.toString();
	}
}

