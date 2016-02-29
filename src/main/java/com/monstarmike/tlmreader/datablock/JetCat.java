package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;

public class JetCat extends DataBlock {
	public enum ECUStatus {
		
	}
	
	public enum OffStatus {
		
	}
	
	public byte get_rawECUStatus() {
		return this.rawData[6];
	}
	
	public byte get_throttlePercentage() {
		return this.rawData[7];
	}
	
	public double get_packVoltage() {
		return (this.rawData[8] * 100D) + (this.rawData[9] / 100D);
	}
	
	public double get_pumpVoltage() {
		return (this.rawData[10] * 100D) + (this.rawData[11] / 100D);
	}
	
	public int get_RPM(){
		return Ints.fromBytes(this.rawData[12], this.rawData[13], this.rawData[14], this.rawData[15]);
	}
	
	public short get_EGT() {
		return Shorts.fromBytes(this.rawData[16], this.rawData[17]);
	}
	
	public byte get_rawOffCondition() {
		return this.rawData[18];
	}
	
	public JetCat(byte[] rawData) {
		super(rawData);
		// TODO Auto-generated constructor stub
	}

}
