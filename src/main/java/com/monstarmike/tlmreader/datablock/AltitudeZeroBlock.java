package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Ints;

public class AltitudeZeroBlock extends DataBlock {
	
//	typedef struct
//	{
//		UINT8		identifier;												// Source device = 0x7B
//		UINT8		sID;															// Secondary ID
//		UINT8		spare[2];
//		UINT32	altOffset;												// Altitude "zero" log
//	} STRU_TELE_ALT_ZERO;

	private int altOffset;
	
	public AltitudeZeroBlock(byte[] rawData) {
		super(rawData);
		altOffset = Ints.fromBytes(rawData[8], rawData[9], rawData[10], rawData[11]);
		measurementNames.add("Altitude Offset");
		measurementUnits.add("m");
		measurementFactors.add(0.1);
		measurementValues.add((int)getAltOffset());
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof AltitudeBlock) {
			AltitudeZeroBlock alt = (AltitudeZeroBlock) block;
			return alt.altOffset == this.altOffset;
		}
		return false;
	}

	public int getAltOffset() {
		return altOffset;
	}

}
