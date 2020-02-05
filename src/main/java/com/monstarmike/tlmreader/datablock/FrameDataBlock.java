package com.monstarmike.tlmreader.datablock;

public class FrameDataBlock extends DataBlock {

	public FrameDataBlock(byte[] rawData) {
		super(rawData);
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		return false;
	}

	@Override
	public String toString() {
		return "FrameData:      " + getTimestamp() 
				+ ", unknown data structure";
	}
}
