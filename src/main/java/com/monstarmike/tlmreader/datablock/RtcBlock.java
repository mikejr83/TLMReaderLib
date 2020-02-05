package com.monstarmike.tlmreader.datablock;

public class RtcBlock extends DataBlock {

//	typedef struct
//	{
//		UINT8		identifier;												// Source device = 0x7C
//		UINT8		sID;															// Secondary ID
//		UINT8		spare[6];
//		UINT64	UTC64;														// Linux 64-bit time_t for post-2038 date compatibility
//	} STRU_TELE_RTC;
	
	public RtcBlock(byte[] rawData) {
		super(rawData);
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		return false;
	}

	@Override
	public String toString() {
		return "RtcData:        " + getTimestamp() 
				+ ", not used data structure";
	}
}
