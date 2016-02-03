package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import com.monstarmike.tlmreader.HeaderBlockBuilder;

public class HeaderVoltBlockTest {

	private static final short VOLT_HEADERBLOCK_MAKER = (short) 0x0101;

	@Test
	public void testIsVoltHeaderOK() {
		byte[] testBlock = new HeaderBlockBuilder(0, VOLT_HEADERBLOCK_MAKER).get();
		Assert.assertTrue(HeaderVoltBlock.isVoltHeader(testBlock));
	}

	@Test
	public void testIsVoltHeaderWrongHeaderMarker() {
		byte[] testBlock = new HeaderBlockBuilder(0, 0x1111).get();
		Assert.assertFalse(HeaderVoltBlock.isVoltHeader(testBlock));
	}

	@Test
	public void testIsVoltHeaderPartlyWrongHeaderMarker() {
		byte[] testBlock = new HeaderBlockBuilder(0, 0x0122).get();
		Assert.assertFalse(HeaderVoltBlock.isVoltHeader(testBlock));
	}

	@Test
	public void testIsVoltHeaderTooShortByteArray() {
		Assert.assertFalse(HeaderVoltBlock.isVoltHeader(new byte[5]));
	}

	@Test
	public void testIsActive() {
		byte[] testBlock = new HeaderBlockBuilder(0, VOLT_HEADERBLOCK_MAKER).setValue(0x0001, 0x06).get();
		HeaderVoltBlock voltHeaderBlock = new HeaderVoltBlock(testBlock);
		assertTrue(voltHeaderBlock.isActive());
	}

	@Test
	public void testGetMinVolt() {
		byte[] testBlock = new HeaderBlockBuilder(0, VOLT_HEADERBLOCK_MAKER).setSwitchedEndianValue((short) 2300, 0x0C)
				.get();
		HeaderVoltBlock voltHeaderBlock = new HeaderVoltBlock(testBlock);
		assertEquals(23.0, voltHeaderBlock.getMinVolt(), 0.001);
	}

	@Test
	public void testGetMaxVolt() {
		byte[] testBlock = new HeaderBlockBuilder(0, VOLT_HEADERBLOCK_MAKER).setSwitchedEndianValue((short) 6000, 0x0E)
				.get();
		HeaderVoltBlock voltHeaderBlock = new HeaderVoltBlock(testBlock);
		assertEquals(60.0, voltHeaderBlock.getMaxVolt(), 0.001);
	}

	@Test
	public void testGetStatusReport5s() {
		byte[] testBlock = new HeaderBlockBuilder(0, VOLT_HEADERBLOCK_MAKER).setValue(0x0100, 0x10).get();
		HeaderVoltBlock voltHeaderBlock = new HeaderVoltBlock(testBlock);
		assertEquals("5 sec", voltHeaderBlock.getStatusReport());
	}

	@Test
	public void testGetWarningReport10s() {
		byte[] testBlock = new HeaderBlockBuilder(0, VOLT_HEADERBLOCK_MAKER).setValue(0x0002, 0x10).get();
		HeaderVoltBlock voltHeaderBlock = new HeaderVoltBlock(testBlock);
		assertEquals("10 sec", voltHeaderBlock.getWarningReport());
	}
}
