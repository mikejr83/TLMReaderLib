package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class VarioBlockTest {

	private static final short VARIO_DATABLOCK_MAKER = (short) 0x4000;

	@Test
	public void testGetAltitude() {
		short altitudeInTenthOfMeters = 50 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(altitudeInTenthOfMeters, 0x6).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(altitudeInTenthOfMeters, varioBlock.getAltitudeInTenthOfMeter());
	}

	@Test
	public void testGetDeltaInIntervalOf250ms() {
		short deltaInTenthOfMeters = 20 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(deltaInTenthOfMeters, 0x08).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(deltaInTenthOfMeters, varioBlock.getDeltaInIntervalOf250MsInTenthOfMeter());
	}

	@Test
	public void testGetDeltaInIntervalOf500ms() {
		short deltaInTenthOfMeters = 4 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(deltaInTenthOfMeters, 0x0A).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(deltaInTenthOfMeters, varioBlock.getDeltaInIntervalOf500MsInTenthOfMeter());
	}

	@Test
	public void testGetDeltaInIntervalOf1000ms() {
		short deltaInTenthOfMeters = 15 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(deltaInTenthOfMeters, 0x0C).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(deltaInTenthOfMeters, varioBlock.getDeltaInIntervalOf1000MsInTenthOfMeter());
	}

	@Test
	public void testGetDeltaInIntervalOf2000ms() {
		short deltaInTenthOfMeters = 23 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(deltaInTenthOfMeters, 0x0E).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(deltaInTenthOfMeters, varioBlock.getDeltaInIntervalOf2000MsInTenthOfMeter());
	}

	@Test
	public void testGetDeltaInIntervalOf3000ms() {
		short deltaInTenthOfMeters = 17 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(deltaInTenthOfMeters, 0x10).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(deltaInTenthOfMeters, varioBlock.getDeltaInIntervalOf3000MsInTenthOfMeter());
	}
}
