package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class AirspeedBlockTest {

	private static final short AIRSPEED_DATABLOCK_MAKER = (short) 0x1100;

	@Test
	public void testGetAirSpeed() {
		int valueInKmPerHour = 123;
		byte[] testBlock = new DataBlockBuilder(0, AIRSPEED_DATABLOCK_MAKER).setValue(valueInKmPerHour, 0x6).get();
		AirspeedBlock airSpeedBlock = (AirspeedBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(valueInKmPerHour, airSpeedBlock.getAirspeedInKmPerHour());
	}

}
