package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class AltitudeZeroBlockTest {

	private static final short ALTITUDE_ZERO_DATABLOCK_MAKER = (short) 0x7B00;

	@Test
	public void testGetAltitudeOffset() {
		byte[] testBlock = new DataBlockBuilder(0, ALTITUDE_ZERO_DATABLOCK_MAKER).setValue(5, 0x9).get();
		AltitudeZeroBlock block = (AltitudeZeroBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(128 , block.getAltOffset() * block.getMeasurementFactors().get(0), 0);
	}
}
