package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;

public final class JetCat extends DataBlock {
	public enum ECUStatus {
		
	}
	
	public enum OffStatus {
		
	}
	
	private byte rawECUStatus;
	private byte throttlePercentage;
	private double packVoltage;
	private double pumpVoltage;
	private int rpm;
	private short egt;
	private byte rawOffCondition;
	
	public byte getRawECUStatus() {
		return this.rawECUStatus;
	}
	
	public byte getThrottlePercentage() {
		return this.throttlePercentage;
	}
	
	public double getPackVoltage() {
		return this.packVoltage;
	}
	
	public double getPumpVoltage() {
		return this.pumpVoltage;
	}
	
	public int getRPM(){
		return this.rpm;
	}
	
	public short getEGT() {
		return this.egt;
	}
	
	public byte getRawOffCondition() {
		return this.rawOffCondition;
	}
	
	public JetCat(byte[] rawData) {
		super(rawData);
		decode(rawData);
	}

	private void decode(byte[] rawData) {
		rawECUStatus = rawData[6];
		throttlePercentage = rawData[7];
		packVoltage = (rawData[8] * 100D) + (rawData[9] / 100D);
		pumpVoltage = (rawData[10] * 100D) + (rawData[11] / 100D);
		rpm = Ints.fromBytes(rawData[12], rawData[13], rawData[14], rawData[15]);
		egt = Shorts.fromBytes(rawData[16], rawData[17]);
		rawOffCondition = rawData[18];
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		// TODO Auto-generated method stub
		return false;
	}
}
