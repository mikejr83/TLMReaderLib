package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class RXBlockTest {

	private static final short RX_DATABLOCK_MAKER = (short) 0x7F00;

	@Test
	public void testValueA() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(123, 0x06).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(123, (short) rxBlock.getA());
	}

	@Test
	public void testValueB() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(739, 0x08).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(739, (short) rxBlock.getB());
	}

	@Test
	public void testValueL() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(999, 0x0A).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(999, (short) rxBlock.getL());
	}

	@Test
	public void testValueR() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(302, 0x0C).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(302, (short) rxBlock.getR());
	}

	@Test
	public void testFrameLoss() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(3, 0x0E).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(3, (short) rxBlock.getFrameLoss());
	}

	@Test
	public void testHolds() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(15, 0x10).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(15, (short) rxBlock.getHolds());
	}

	@Test
	public void testVolts() {
		int valueInHunderthOfVolt = 603;
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(valueInHunderthOfVolt, 0x12).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(valueInHunderthOfVolt, rxBlock.getVoltageInHunderthOfVolts());
	}
}
