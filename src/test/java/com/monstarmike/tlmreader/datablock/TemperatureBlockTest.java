package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class TemperatureBlockTest {

	private static final short TEMPERATURE_DATABLOCK_MAKER = (short) 0x0200;

	@Test
	public void testGetTemperatureInFahrenheit() {
		byte[] testBlock = new DataBlockBuilder(0, TEMPERATURE_DATABLOCK_MAKER).setValue(87, 0x6).get();
		TemperatureBlock block = (TemperatureBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(87 , block.getTemperatureInFahrenheit());
	}

	@Test
	public void testGetTemperatureInCelsius() {
		byte[] testBlock = new DataBlockBuilder(0, TEMPERATURE_DATABLOCK_MAKER).setValue(87, 0x6).get();
		TemperatureBlock block = (TemperatureBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(((87 - 32) / 1.8f) , block.getTemperatureInThenthGradCelsius() * block.getMeasurementFactors().get(0), 0.1);
	}
}
