package com.monstarmike.tlmreader.datablock;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

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
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.get_rpm(), 0.02);
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
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x06).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.get_rpm(), 0.001);
	}
	
	@Test
	public void testGetRpm() {
		byte poles = (byte)6;
		double ratio = 8.0;
		HeaderRpmBlock rpmHeaderMock = Mockito.mock(HeaderRpmBlock.class);
		Mockito.when(rpmHeaderMock.getPoles()).thenReturn(poles);
		Mockito.when(rpmHeaderMock.getRatio()).thenReturn(ratio);
		short testValue = (short) 0xFFFF;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x06).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, rpmHeaderMock );
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue) / poles / ratio, standardBlock.get_rpm(), 0.001);
	}
	
	@Test
	public void testGetVolt() {
		short testValue = (short) 1163;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x08).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(11.63, standardBlock.get_volt(), 0.001);
	}

	@Test
	public void testGetTemperatureInGradFahrenheit() {
		short testValue = (short) 87;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x0A).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(87, standardBlock.get_temperatureInGradFahrenheit());
	}

	@Test
	public void testGetTemperatureInGradCelcius() {
		short testValue = (short) 87;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x0A).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals((87 - 32) / 1.8,  standardBlock.get_temperatureInGradCelsius(), 0.001);
	}
	
	private double reverseEngineeredRpmValueFunction(final short rawShortValue) {
		double rpmValue = (rawShortValue == 0) ? 0.0 : 1.0 / rawShortValue * 120000000.0;
		return rpmValue;
	}
}
