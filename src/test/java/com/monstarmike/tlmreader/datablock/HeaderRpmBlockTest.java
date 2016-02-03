package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class HeaderRpmBlockTest {

	private static final short RPM_HEADERBLOCK_MAKER = (short) 0x7E7E;

	@Test
	public void testIsRpmHeaderOK() {
		byte[] testBlock = new DataBlockBuilder(0, RPM_HEADERBLOCK_MAKER).get();
		Assert.assertTrue(HeaderRpmBlock.isRpmHeader(testBlock));
	}
	
	@Test
	public void testIsRpmHeaderWrongHeaderMarker() {
		byte[] testBlock = new DataBlockBuilder(0, 0x1111).get();
		Assert.assertFalse(HeaderRpmBlock.isRpmHeader(testBlock));
	}
	
	@Test
	public void testIsRpmHeaderPartlyWrongHeaderMarker() {
		byte[] testBlock = new DataBlockBuilder(0, 0x7E11).get();
		Assert.assertFalse(HeaderRpmBlock.isRpmHeader(testBlock));
	}
	
	@Test
	public void testIsRpmHeaderTooShortByteArray() {
		Assert.assertFalse(HeaderRpmBlock.isRpmHeader(new byte[5]));
	}

	@Test
	public void testGetPoles() {
		byte[] testBlock = new DataBlockBuilder(0, RPM_HEADERBLOCK_MAKER).setValue(0x0600, 0x06).get();
		HeaderRpmBlock rpmHeaderBlock = new HeaderRpmBlock(testBlock);
		assertEquals(6, (byte)rpmHeaderBlock.getPoles());
	}
	
	@Test
	public void testIsActive() {
		byte[] testBlock = new DataBlockBuilder(0, RPM_HEADERBLOCK_MAKER).setValue(0x0001, 0x06).get();
		HeaderRpmBlock rpmHeaderBlock = new HeaderRpmBlock(testBlock);
		assertTrue(rpmHeaderBlock.isActive());
	}
	@Test
	public void testGetRatio() {
		short ratio = 8 * 100;
		byte[] testBlock = new DataBlockBuilder(0, RPM_HEADERBLOCK_MAKER).setSwitchedEndianValue(ratio, 0x0A).get();
		HeaderRpmBlock rpmHeaderBlock = new HeaderRpmBlock(testBlock);
		assertEquals(8.00, (double)rpmHeaderBlock.getRatio(), 0.001);
	}
	@Test
	public void testGetMinRpm() {
		byte[] testBlock = new DataBlockBuilder(0, RPM_HEADERBLOCK_MAKER).setSwitchedEndianValue((short)2300, 0x0C).get();
		HeaderRpmBlock rpmHeaderBlock = new HeaderRpmBlock(testBlock);
		assertEquals(2300, (short)rpmHeaderBlock.getMinRpm());
	}
	@Test
	public void testGetMaxRpm() {
		byte[] testBlock = new DataBlockBuilder(0, RPM_HEADERBLOCK_MAKER).setSwitchedEndianValue((short)6000, 0x0E).get();
		HeaderRpmBlock rpmHeaderBlock = new HeaderRpmBlock(testBlock);
		assertEquals(6000, (short)rpmHeaderBlock.getMaxRpm());
	}
	@Test
	public void testGetStatusReport5s() {
		byte[] testBlock = new DataBlockBuilder(0, RPM_HEADERBLOCK_MAKER).setValue(0x0100, 0x10).get();
		HeaderRpmBlock rpmHeaderBlock = new HeaderRpmBlock(testBlock);
		assertEquals("5 sec", rpmHeaderBlock.getStatusReport());
	}
	@Test
	public void testGetWarningReport10s() {
		byte[] testBlock = new DataBlockBuilder(0, RPM_HEADERBLOCK_MAKER).setValue(0x0002, 0x10).get();
		HeaderRpmBlock rpmHeaderBlock = new HeaderRpmBlock(testBlock);
		assertEquals("10 sec", rpmHeaderBlock.getWarningReport());
	}
}
