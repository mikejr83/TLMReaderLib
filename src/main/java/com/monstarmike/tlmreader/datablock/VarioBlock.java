package com.monstarmike.tlmreader.datablock;

import com.google.common.primitives.Shorts;

public class VarioBlock extends DataBlock {

//	typedef struct
//	{
//		UINT8		identifier;														// Source device = 0x40
//		UINT8		sID;																	// Secondary ID
//		INT16		altitude;															// .1m increments
//		INT16		delta_0250ms,													// change in altitude last 250ms, 0.1m/s increments
//						delta_0500ms,													// change in altitude last 500ms, 0.1m/s increments
//						delta_1000ms,													// change in altitude last 1.0 seconds, 0.1m/s increments
//						delta_1500ms,													// change in altitude last 1.5 seconds, 0.1m/s increments
//						delta_2000ms,													// change in altitude last 2.0 seconds, 0.1m/s increments
//						delta_3000ms;													// change in altitude last 3.0 seconds, 0.1m/s increments
//	} STRU_TELE_VARIO_S;
	
	private short altitudeInTenthOfMeter;
	private short deltaInIntervalOf250MsInTenthOfMeter;
	private short deltaInIntervalOf500MsInTenthOfMeter;
	private short deltaInIntervalOf1000MsInTenthOfMeter;
	private short deltaInIntervalOf2000MsInTenthOfMeter;
	private short deltaInIntervalOf3000MsInTenthOfMeter;

	public VarioBlock(byte[] rawData) {
		super(rawData);
		decode(rawData);
		
		measurementNames.add("Altitude V");
		measurementNames.add("Climb V");

		measurementUnits.add("m");
		measurementUnits.add("m/s");

		measurementFactors.add(0.1);
		measurementFactors.add(0.1);
	}

	@Override
	public boolean areValuesEquals(DataBlock block) {
		if (block instanceof VarioBlock) {
			VarioBlock vario = (VarioBlock) block;
			return vario.altitudeInTenthOfMeter != altitudeInTenthOfMeter
					&& vario.deltaInIntervalOf250MsInTenthOfMeter != deltaInIntervalOf250MsInTenthOfMeter
					&& vario.deltaInIntervalOf500MsInTenthOfMeter != deltaInIntervalOf500MsInTenthOfMeter
					&& vario.deltaInIntervalOf1000MsInTenthOfMeter != deltaInIntervalOf1000MsInTenthOfMeter
					&& vario.deltaInIntervalOf2000MsInTenthOfMeter != deltaInIntervalOf2000MsInTenthOfMeter
					&& vario.deltaInIntervalOf3000MsInTenthOfMeter != deltaInIntervalOf3000MsInTenthOfMeter;
		}
		return false;
	}

	public short getAltitudeInTenthOfMeter() {
		return altitudeInTenthOfMeter;
	}

	public short getDeltaInIntervalOf250MsInTenthOfMeter() {
		return deltaInIntervalOf250MsInTenthOfMeter;
	}

	public short getDeltaInIntervalOf500MsInTenthOfMeter() {
		return deltaInIntervalOf500MsInTenthOfMeter;
	}

	public short getDeltaInIntervalOf1000MsInTenthOfMeter() {
		return deltaInIntervalOf1000MsInTenthOfMeter;
	}

	public short getDeltaInIntervalOf2000MsInTenthOfMeter() {
		return deltaInIntervalOf2000MsInTenthOfMeter;
	}

	public short getDeltaInIntervalOf3000MsInTenthOfMeter() {
		return deltaInIntervalOf3000MsInTenthOfMeter;
	}

	private void decode(byte[] rawData) {
		altitudeInTenthOfMeter = Shorts.fromBytes(rawData[6], rawData[7]);
		deltaInIntervalOf250MsInTenthOfMeter = Shorts.fromBytes(rawData[8], rawData[9]);
		deltaInIntervalOf500MsInTenthOfMeter = Shorts.fromBytes(rawData[10], rawData[11]);
		deltaInIntervalOf1000MsInTenthOfMeter = Shorts.fromBytes(rawData[12], rawData[13]);
		deltaInIntervalOf2000MsInTenthOfMeter = Shorts.fromBytes(rawData[14], rawData[15]);
		deltaInIntervalOf3000MsInTenthOfMeter = Shorts.fromBytes(rawData[16], rawData[17]);
		
		measurementValues.add((int)getAltitudeInTenthOfMeter());
		measurementValues.add((int)getDeltaInIntervalOf250MsInTenthOfMeter());
	}

}
