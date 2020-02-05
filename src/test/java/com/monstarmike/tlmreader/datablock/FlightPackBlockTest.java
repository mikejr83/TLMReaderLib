package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class FlightPackBlockTest {

	private static final short DATABLOCK_MAKER = (short) 0x3400;
	private static final byte[] BLOCK_DATA = new byte[] { (byte)0x71, (byte)0x9E, (byte)0x0B, (byte)0x00, (byte)0x34, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x52, (byte)0x03, (byte)0xAF, (byte)0x00, (byte)0xFF, (byte)0x7F, (byte)0xFF, (byte)0x7F, (byte)0xFF, (byte)0x7F, (byte)0x00, (byte)0x00}
;

	@Test
	public void testGetCurrentB() {
		byte[] testBlock = new DataBlockBuilder(0, DATABLOCK_MAKER).setValue(22, 11).get();
		FlightPackBlock block = (FlightPackBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(22 * 0.1 , block.getCurrentBInTenthOfAmps() * block.getMeasurementFactors().get(3), 0.1);
	}

	@Test
	public void testToString() {
		FlightPackBlock block = new FlightPackBlock(BLOCK_DATA);
		assertEquals("FlightPackBlock   761457 - Current FPA = 0.2 A; Capacity FPA = 850 mAh; Temperature FPA = 17.5 °C; Current FPB = 0.0 A; Capacity FPB = 0 mAh; Temperature FPB = 0.0 °C;", 
				block.toString().trim()
				);
	}
}
