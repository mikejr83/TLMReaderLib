package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

public class RXBlockTest {

	@Test
	public void testValueA() {
		byte[] testBlock = createRXTestBlock();
		setValueAtPosition(testBlock, (short) 123, 0);
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(123, (short) rxBlock.get_a());
	}

	@Test
	public void testValueB() {
		byte[] testBlock = createRXTestBlock();
		setValueAtPosition(testBlock, (short) 739, 1);
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(739, (short) rxBlock.get_b());
	}

	@Test
	public void testValueL() {
		byte[] testBlock = createRXTestBlock();
		setValueAtPosition(testBlock, (short) 999, 2);
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(999, (short) rxBlock.get_l());
	}

	@Test
	public void testValueR() {
		byte[] testBlock = createRXTestBlock();
		setValueAtPosition(testBlock, (short) 302, 3);
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(302, (short) rxBlock.get_r());
	}

	@Test
	public void testFrameLoss() {
		byte[] testBlock = createRXTestBlock();
		setValueAtPosition(testBlock, (short) 3, 4);
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(3, (short) rxBlock.get_frameLoss());
	}

	@Test
	public void testHolds() {
		byte[] testBlock = createRXTestBlock();
		setValueAtPosition(testBlock, (short) 15, 5);
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(15, (short) rxBlock.get_holds());
	}

	@Test
	public void testVolts() {
		byte[] testBlock = createRXTestBlock();
		setVoltsAtPosition(testBlock, 6.03, 6);
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(6.03, rxBlock.get_volts(), 0.005);
	}

	private byte[] createRXTestBlock() {
		byte[] testBlock = new byte[20];
		testBlock[4] = (byte) 0x7F; // marker for StandardBlock
		return testBlock;
	}

	private void setValueAtPosition(byte[] dataBlock, short value, int position) {
		dataBlock[4 + 2 + position * 2] = (byte) ((value & 0xFF00) >> 8);
		dataBlock[4 + 2 + position * 2 + 1] = (byte) (value & 0xFF);
	}

	private void setVoltsAtPosition(byte[] dataBlock, double volts, int position) {
		short value = new BigDecimal(volts * 100).setScale(0, RoundingMode.CEILING).shortValue();
		setValueAtPosition(dataBlock, value, position);
	}

}
