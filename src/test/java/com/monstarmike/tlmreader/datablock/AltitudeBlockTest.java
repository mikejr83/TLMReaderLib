package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class AltitudeBlockTest {

	private static final short ALTITUDE_DATABLOCK_MAKER = (short) 0x1200;

	@Test
	public void testGetAltitude() {
		byte[] testBlock = new DataBlockBuilder(0, ALTITUDE_DATABLOCK_MAKER).setValue(315, 0x6).get();
		AltitudeBlock block = (AltitudeBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(315 * 0.1 , block.getAltitudeInTenthOfMeter() * block.getMeasurementFactors().get(0), 0.1);
	}
}
