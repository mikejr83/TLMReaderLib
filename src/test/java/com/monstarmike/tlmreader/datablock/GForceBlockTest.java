package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class GForceBlockTest {

	private static final short GFORCE_DATABLOCK_MAKER = (short) 0x1400;

	@Test
	public void testGetXInHunderthsOfG() {
		byte[] testBlock = new DataBlockBuilder(0, GFORCE_DATABLOCK_MAKER).setValue(163, 0x6).get();
		GForceBlock gforceBlock = (GForceBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(163 , gforceBlock.get_x());
	}
	
	@Test
	public void testGetYInHunderthsOfG() {
		byte[] testBlock = new DataBlockBuilder(0, GFORCE_DATABLOCK_MAKER).setValue(283, 0x8).get();
		GForceBlock gforceBlock = (GForceBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(283 , gforceBlock.get_y());
	}

	@Test
	public void testGetZInHunderthsOfG() {
		byte[] testBlock = new DataBlockBuilder(0, GFORCE_DATABLOCK_MAKER).setValue(117, 0x0A).get();
		GForceBlock gforceBlock = (GForceBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(117 , gforceBlock.get_z());
	}
	
	@Test
	public void testGetMaxXInHunderthsOfG() {
		byte[] testBlock = new DataBlockBuilder(0, GFORCE_DATABLOCK_MAKER).setValue(426, 0x0C).get();
		GForceBlock gforceBlock = (GForceBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(426, gforceBlock.get_maxX());
	}
	
	@Test
	public void testGetMaxYInHunderthsOfG() {
		byte[] testBlock = new DataBlockBuilder(0, GFORCE_DATABLOCK_MAKER).setValue(-119, 0x0E).get();
		GForceBlock gforceBlock = (GForceBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(-119, gforceBlock.get_maxY());
	}
	
	@Test
	public void testGetMaxZInHunderthsOfG() {
		byte[] testBlock = new DataBlockBuilder(0, GFORCE_DATABLOCK_MAKER).setValue(887, 0x10).get();
		GForceBlock gforceBlock = (GForceBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(887, gforceBlock.get_maxZ());
	}
	
	@Test
	public void testGetMinZInHunderthsOfG() {
		byte[] testBlock = new DataBlockBuilder(0, GFORCE_DATABLOCK_MAKER).setValue(14, 0x12).get();
		GForceBlock gforceBlock = (GForceBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(14, gforceBlock.get_minZ());
	}
	


}
