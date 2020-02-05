package com.monstarmike.tlmreader.datablock;

public class RxPackCapacityBlock extends DataBlock {

	public RxPackCapacityBlock(byte[] rawData) {
		super(rawData);
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		return false;
	}

	@Override
	public String toString() {
		return "RtcData:        " + getTimestamp() 
				+ ", unknown data structure";
	}
}
