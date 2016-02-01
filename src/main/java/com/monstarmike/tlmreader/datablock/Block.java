package com.monstarmike.tlmreader.datablock;

import com.monstarmike.tlmreader.util.StringUtils;

public abstract class Block {

	byte[] rawData;
	
	public abstract int getSize();
	
	public Block(byte[] rawData) {
		this.rawData = rawData;
	}
	
	@Override
	public String toString() {
		return StringUtils.bytesToHex(this.rawData);
	}
}
