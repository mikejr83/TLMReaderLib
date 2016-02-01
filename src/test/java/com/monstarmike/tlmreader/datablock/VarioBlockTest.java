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
		assertEquals(altitudeInTenthOfMeters, (short)varioBlock.get_altitude());
	}
	
	@Test
	public void testGetChangeInLast250ms() {
		short changeInTenthOfMeters = 20 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(changeInTenthOfMeters, 0x08).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(changeInTenthOfMeters, (short)varioBlock.get_250ms());
	}
	
	@Test
	public void testGetChangeInLast500ms() {
		short changeInTenthOfMeters = 4 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(changeInTenthOfMeters, 0x0A).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(changeInTenthOfMeters, (short)varioBlock.get_500ms());
	}

	@Test
	public void testGetChangeInLast1000ms() {
		short changeInTenthOfMeters = 15 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(changeInTenthOfMeters, 0x0C).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(changeInTenthOfMeters, (short)varioBlock.get_1000ms());
	}
	
	@Test
	public void testGetChangeInLast2000ms() {
		short changeInTenthOfMeters = 23 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(changeInTenthOfMeters, 0x0E).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(changeInTenthOfMeters, (short)varioBlock.get_2000ms());
	}
	
	@Test
	public void testGetChangeInLast3000ms() {
		short changeInTenthOfMeters = 17 * 10;
		byte[] testBlock = new DataBlockBuilder(0, VARIO_DATABLOCK_MAKER).setValue(changeInTenthOfMeters, 0x10).get();
		VarioBlock varioBlock = (VarioBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(changeInTenthOfMeters, (short)varioBlock.get_3000ms());
	}
}
