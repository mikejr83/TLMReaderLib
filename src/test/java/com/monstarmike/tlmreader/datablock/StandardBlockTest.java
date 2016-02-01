package com.monstarmike.tlmreader.datablock;

import org.junit.Assert;
import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class StandardBlockTest {

	private static final short STD_DATABLOCK_MAKER = (short) 0xFE00;

	@Test
	public void testReadRpmZeroNoRpmHeader() {
		short testValue = (short) 0x0000;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.get_rpm(), 0.001);
	}

	@Test
	public void testReadRpm1111tZeroNoRpmHeader() {
		short testValue = (short) 0x1111;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.get_rpm(), 0.001);
	}

	@Test
	public void testReadRpm00FFZeroNoRpmHeader() {
		short testValue = (short) 0x00FF;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.get_rpm(), 0.001);
	}

	@Test
	public void testReadRpmFF00ZeroNoRpmHeader() {
		short testValue = (short) 0xFF00;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.get_rpm(), 0.001);
	}

	@Test
	public void testReadRpmFFFFZeroNoRpmHeader() {
		short testValue = (short) 0xFFFF;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.get_rpm(), 0.001);
	}

	private double reverseEngineeredRpmValueFunction(final short rawShortValue) {
		double rpmValue = (rawShortValue == 0) ? 0.0 : 1.0 / rawShortValue * 120000000.0;
		return rpmValue;
	}
}
