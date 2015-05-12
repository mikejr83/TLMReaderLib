package com.monstarmike.tlmreader.datablock;

public abstract class Block {

	byte[] rawTimestamp;
	byte rawDataType;
	byte rawFlag;
	byte rawData;
	
	public abstract int getSize();
	
	public byte[] getRawTimestamp() {
		return rawTimestamp;
	}
	public void setRawTimestamp(byte[] rawTimestamp) {
		this.rawTimestamp = rawTimestamp;
	}
	public byte getRawDataType() {
		return rawDataType;
	}
	public void setRawDataType(byte rawDataType) {
		this.rawDataType = rawDataType;
	}
	public byte getRawFlag() {
		return rawFlag;
	}
	public void setRawFlag(byte rawFlag) {
		this.rawFlag = rawFlag;
	}
	
	
}
