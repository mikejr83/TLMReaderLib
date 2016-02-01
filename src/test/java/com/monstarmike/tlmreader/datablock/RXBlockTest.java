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
		assertEquals(123, (short) rxBlock.get_a());
	}

	@Test
	public void testValueB() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(739, 0x08).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(739, (short) rxBlock.get_b());
	}

	@Test
	public void testValueL() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(999, 0x0A).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(999, (short) rxBlock.get_l());
	}

	@Test
	public void testValueR() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(302, 0x0C).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(302, (short) rxBlock.get_r());
	}

	@Test
	public void testFrameLoss() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(3, 0x0E).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(3, (short) rxBlock.get_frameLoss());
	}

	@Test
	public void testHolds() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(15, 0x10).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(15, (short) rxBlock.get_holds());
	}

	@Test
	public void testVolts() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValueWithScale2(6.03f, 0x12).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(6.03, rxBlock.get_volts(), 0.005);
	}
}
