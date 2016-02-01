package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

public class AirspeedBlockTest {

	@Test
	public void testGetAirSpeed() {
		byte[] testBlock = createAirspeedTestBlock();
		setValueAtPosition(testBlock, (short) 123, 0);
		AirspeedBlock airSpeedBlock = (AirspeedBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(123, airSpeedBlock.get_airspeed());
	}

	private byte[] createAirspeedTestBlock() {
		byte[] testBlock = new byte[20];
		testBlock[4] = (byte) 0x11; // marker for AirspeedBlock
		return testBlock;
	}

	private void setValueAtPosition(byte[] dataBlock, short value, int position) {
		dataBlock[4 + 2 + position * 2] = (byte) ((value & 0xFF00) >> 8);
		dataBlock[4 + 2 + position * 2 + 1] = (byte) (value & 0xFF);
	}


	
}
