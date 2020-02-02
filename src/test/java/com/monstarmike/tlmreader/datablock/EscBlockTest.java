package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class EscBlockTest {

	private static final short ESC_DATABLOCK_MAKER = (short) 0x2000;
	private static final byte[] ESC_BLOCK_DATA = new byte[] { (byte)0x8A, (byte)0x9E, (byte)0x0B, (byte)0x00, (byte)0x20, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x04, (byte)0x88, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x13, (byte)0x7F, (byte)0xFF, (byte)0x00, (byte)0x6E, (byte)0xFF, (byte)0x52};

	@Test
	public void testGetCurrentBEC() {
		byte[] testBlock = new DataBlockBuilder(0, ESC_DATABLOCK_MAKER).setValue(22, 15).get();
		EscBlock block = (EscBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(22 * 0.1 , block.getCurrentBECInTenthOfAmps() * block.getMeasurementFactors().get(4), 0.1);
	}

	@Test
	public void testToString() {
		EscBlock block = new EscBlock(ESC_BLOCK_DATA);
		assertEquals("EscBlock          761482 - RPM ESC = 0.0 1/min; Voltage ESC = 11.6 V; TempFET ESC = 0.0 °C; Current ESC = 0.2 A; CurrentBEC ESC = 0.0 A; VoltsBEC ESC = 5.5 V; Throttle ESC = 0.0 %; PowerOut ESC = 41.0 %; PowerIn ESC = 2.2 W;", 
				block.toString().trim()
				);
	}
}
