package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class PowerboxBlockTest {

	private static final short POWERBOX_DATABLOCK_MAKER = (short) 0x0a00;

	@Test
	public void testGetVoltageOne() {
		short valueInHunderthOfVolt = 473; // = 4.73 Volt
		byte[] testBlock = new DataBlockBuilder(0, POWERBOX_DATABLOCK_MAKER).setValue(valueInHunderthOfVolt, 0x06).get();
		PowerboxBlock powerBoxBlock = (PowerboxBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(0.01 * valueInHunderthOfVolt , powerBoxBlock.get_voltageOne(), 0.001);
	}
	@Test
	public void testGetVoltageTwo() {
		short valueInHunderthOfVolt = 1620; // = 16.20 Volt
		byte[] testBlock = new DataBlockBuilder(0, POWERBOX_DATABLOCK_MAKER).setValue(valueInHunderthOfVolt, 0x08).get();
		PowerboxBlock powerBoxBlock = (PowerboxBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(0.01 * valueInHunderthOfVolt, powerBoxBlock.get_voltageTwo(), 0.001);
	}
	
	@Test
	public void testGetCapacityOne() {
		short valueIn_mAh = 15;
		byte[] testBlock = new DataBlockBuilder(0, POWERBOX_DATABLOCK_MAKER).setValue(valueIn_mAh, 0x0A).get();
		PowerboxBlock powerBoxBlock = (PowerboxBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(valueIn_mAh, (short) powerBoxBlock.get_capacityOne());
	}
	
	@Test
	public void testGetCapacityTwo() {
		short valueIn_mAh = 763;
		byte[] testBlock = new DataBlockBuilder(0, POWERBOX_DATABLOCK_MAKER).setValue(valueIn_mAh, 0x0C).get();
		PowerboxBlock powerBoxBlock = (PowerboxBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(valueIn_mAh, (short) powerBoxBlock.get_capacityTwo());
	}

}
