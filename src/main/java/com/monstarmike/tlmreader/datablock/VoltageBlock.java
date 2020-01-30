package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class VoltageBlock extends DataBlock {
	
//	typedef struct
//	{
//		UINT8		identifier;													// Source device = 0x01
//		UINT8		sID;																// Secondary ID
//		UINT16	volts;															// 0.01V increments
//	} STRU_TELE_HV;

	private short voltsInTenthOfVolt;
	
	public VoltageBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
		
		measurementNames.add("Voltage V");
		measurementUnits.add("V");
		measurementFactors.add(0.1);
	}
	
	private void decode(byte[] rawData) {
		voltsInTenthOfVolt = Shorts.fromBytes(rawData[2], rawData[3]);
		
		measurementValues.add((int)getVoltsInTenthOfVolt());
	}
	
	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof VoltageBlock) {
			VoltageBlock voltage = (VoltageBlock) block;
			return voltage.voltsInTenthOfVolt == getVoltsInTenthOfVolt();
		}
		return false;
	}

	/**
	 * @return the voltsInTenthOfVolt
	 */
	public short getVoltsInTenthOfVolt() {
		return voltsInTenthOfVolt;
	}
}
