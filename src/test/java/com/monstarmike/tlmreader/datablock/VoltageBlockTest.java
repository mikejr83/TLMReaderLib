package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class VoltageBlockTest {

	private static final short VOLTAGE_DATABLOCK_MAKER = (short) 0x0100;

	@Test
	public void testGetVoltage() {
		byte[] testBlock = new DataBlockBuilder(0, VOLTAGE_DATABLOCK_MAKER).setValue(45, 0x6).get();
		VoltageBlock block = (VoltageBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(45 * 0.1 , block.getVoltsInTenthOfVolt() * block.getMeasurementFactors().get(0), 0.1);
	}
}
