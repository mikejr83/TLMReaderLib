package com.monstarmike.tlmreader.datablock;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;

public class StandardBlockTest {

	@Test
	public void testReadRpmZeroNoRpmHeader() {
		short testValue = (short) 0x0000;
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(createRpmTestBlock(testValue), null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), wrapDouble(standardBlock.get_rpm()));
	}

	@Test
	public void testReadRpm1111tZeroNoRpmHeader() {
		short testValue = (short) 0x1111;
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(createRpmTestBlock(testValue), null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), wrapDouble(standardBlock.get_rpm()));
	}

	@Test
	public void testReadRpm00FFZeroNoRpmHeader() {
		short testValue = (short) 0x00FF;
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(createRpmTestBlock(testValue), null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), wrapDouble(standardBlock.get_rpm()));
	}

	@Test
	public void testReadRpmFF00ZeroNoRpmHeader() {
		short testValue = (short) 0xFF00;
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(createRpmTestBlock(testValue), null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), wrapDouble(standardBlock.get_rpm()));
	}

	@Test
	public void testReadRpmFFFFZeroNoRpmHeader() {
		short testValue = (short) 0xFFFF;
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(createRpmTestBlock(testValue), null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), wrapDouble(standardBlock.get_rpm()));
	}

	private byte[] createRpmTestBlock(final short value) {
		byte[] testBlock = new byte[20];
		testBlock[4] = (byte) 0xFE; // marker for StandardBlock
		testBlock[6] = (byte) ((value & 0xFF00) >> 8);
		testBlock[7] = (byte) (value & 0xFF);
		return testBlock;
	}

	private BigDecimal wrapDouble(double value) {
		return new BigDecimal(Double.toString(value)).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal reverseEngineeredRpmValueFunction(final short rawShortValue) {
		double rpmValue = (rawShortValue == 0) ? 0.0 : 1.0 / rawShortValue * 120000000.0;
		return new BigDecimal(rpmValue).setScale(2, RoundingMode.HALF_UP);
	}
}
