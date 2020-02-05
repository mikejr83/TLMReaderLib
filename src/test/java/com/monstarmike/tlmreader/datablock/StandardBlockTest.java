package com.monstarmike.tlmreader.datablock;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class StandardBlockTest {

	private static final short STD_DATABLOCK_MAKER = (short) 0xFE00;

	@Test
	public void testHasValidRpmData0x0000() {
		int testValue = 0x0000;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertFalse(standardBlock.hasValidRpmData());
	}

	@Test
	public void testHasValidRpmDataShortMAX() {
		int testValue = 0xFFFF;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertFalse(standardBlock.hasValidRpmData());
	}

	@Test
	public void testHasValidRpmDataOk() {
		int testValue = 0x1234;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertTrue(standardBlock.hasValidRpmData());
	}

	@Test
	public void testGetRpm0x0000() {
		int testValue = 0x0000;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.getRpm(), 0.01);
	}

	@Test
	public void testGetRpm0x1111() {
		int testValue = 0x1111;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.getRpm(), 0.01);
	}

	@Test
	public void testGetRpm0x00FF() {
		int testValue = 0x00FF;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.getRpm(), 0.04);
	}

	@Test
	public void testGetRpm0xFF00() {
		int testValue = 0xFF00;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x6).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue), standardBlock.getRpm(), 0.001);
	}

	@Test
	public void testGetRpmWithRationAndPoles() {
		byte poles = (byte) 6;
		short ratioInHunderth = 800;
		HeaderRpmBlock rpmHeaderMock = Mockito.mock(HeaderRpmBlock.class);
		Mockito.when(rpmHeaderMock.getPoles()).thenReturn(poles);
		Mockito.when(rpmHeaderMock.getRatioInHunderth()).thenReturn(ratioInHunderth);
		int testValue = 0x0FFF;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x06).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, rpmHeaderMock);
		Assert.assertEquals(reverseEngineeredRpmValueFunction(testValue) / poles / ratioInHunderth * 100,
				standardBlock.getRpm(), 0.001);
	}

	@Test
	public void testGetVolt() {
		short valueInHunderthOfVolts = (short) 1163;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(valueInHunderthOfVolts, 0x08).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(valueInHunderthOfVolts, standardBlock.getVoltageInHunderthOfVolts());
	}

	@Test
	public void testGetTemperatureInGradFahrenheit() {
		short testValue = (short) 87;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x0A).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(87, standardBlock.getTemperatureInDegreeFahrenheit());
	}

	@Test
	public void testGetTemperatureInGradCelcius() {
		short testValue = (short) 87;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x0A).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals((87 - 32) / 1.8f * 10, standardBlock.getTemperatureInThenthOfDegreeCelsius(), 0.01);
	}

	@Test
	public void testHasValidTemperatureData0x0000() {
		short testValue = (short) 0;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x0A).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertFalse(standardBlock.hasValidTemperatureData());
	}

	@Test
	public void testHasValidTemperatureDataShortMIN() {
		short testValue = (short) 0x7FFF;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x0A).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertFalse(standardBlock.hasValidTemperatureData());
	}

	@Test
	public void testHasValidTemperatureDataOk() {
		short testValue = (short) 0x0011;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x0A).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertTrue(standardBlock.hasValidTemperatureData());
	}

	@Test
	public void testHasValidVoltageData0x0000() {
		short testValue = (short) 0;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x08).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertFalse(standardBlock.hasValidVoltageData());
	}

	@Test
	public void testHasValidVoltageDataShortMIN() {
		short testValue = (short) Short.MIN_VALUE;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x08).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertFalse(standardBlock.hasValidVoltageData());
	}

	@Test
	public void testHasValidVoltageDataOk() {
		short testValue = (short) 0x0011;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testValue, 0x08).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertTrue(standardBlock.hasValidVoltageData());
	}

	@Test
	public void testToString() {
		short testVoltageValue = (short) 0x0723;
		short testTempValue = (short) 0x0057;
		short testRpmValue = (short) 40560;
		byte[] testBlock = new DataBlockBuilder(0, STD_DATABLOCK_MAKER).setValue(testVoltageValue, 0x08)
				.setValue(testTempValue, 0x0A).setValue(testRpmValue, 0x06).get();
		StandardBlock standardBlock = (StandardBlock) DataBlock.createDataBlock(testBlock, null);
		Assert.assertEquals(
				"StandardBlock     0 - RPM St = 2958 1/min; Volt St = 18.3 V; Temperature St = 30.5 °C; dbm_A = 0 dBm; dbm_B = 0 dBm; ",
				standardBlock.toString());
	}

	private float reverseEngineeredRpmValueFunction(final int rawShortValue) {
		return (rawShortValue == 0) ? 0.0f : 1.0f / rawShortValue * 120000000.0f;
	}
}
