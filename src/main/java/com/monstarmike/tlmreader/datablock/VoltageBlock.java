package com.monstarmike.tlmreader.datablock;

public class VoltageBlock extends DataBlock {

	public VoltageBlock(byte[] rawData) {
		super(rawData);
	}
	@Override
	public boolean areValuesEquals(DataBlock block) {
		return false;
	}
}
