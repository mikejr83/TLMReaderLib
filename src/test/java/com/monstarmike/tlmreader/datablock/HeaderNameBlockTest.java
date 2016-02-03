package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.monstarmike.tlmreader.HeaderBlockBuilder;

public class HeaderNameBlockTest {

	private static final short NAME_HEADERBLOCK_MAKER = (short) 0x0102;

	@Test
	public void testIsRpmHeaderOK() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).get();
		Assert.assertTrue(HeaderNameBlock.isHeaderName(testBlock));
	}

	@Test
	public void testIsRpmHeaderWrongHeaderMarker() {
		byte[] testBlock = new HeaderBlockBuilder(0, 0x1111).get();
		Assert.assertFalse(HeaderNameBlock.isHeaderName(testBlock));
	}

	@Test
	public void testIsRpmHeaderTooShortByteArray() {
		Assert.assertFalse(HeaderRpmBlock.isRpmHeader(new byte[5]));
	}

	@Test
	public void testGetBindInfo_DSM2_6000() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).setValue(0x0100, 0x06).get();
		HeaderNameBlock headerNameBlock = new HeaderNameBlock(testBlock);
		assertEquals("DSM2 6000", headerNameBlock.get_bindInfo());
	}

	@Test
	public void testGetBindInfo_DSM2_8000() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).setValue(0x0200, 0x06).get();
		HeaderNameBlock headerNameBlock = new HeaderNameBlock(testBlock);
		assertEquals("DSM2 8000", headerNameBlock.get_bindInfo());
	}

	@Test
	public void testGetBindInfo_DSMX_8000() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).setValue(0x0300, 0x06).get();
		HeaderNameBlock headerNameBlock = new HeaderNameBlock(testBlock);
		assertEquals("DSMX 8000", headerNameBlock.get_bindInfo());
	}

	@Test
	public void testGetBindInfo_DSMX_6000() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).setValue(0x0400, 0x06).get();
		HeaderNameBlock headerNameBlock = new HeaderNameBlock(testBlock);
		assertEquals("DSMX 6000", headerNameBlock.get_bindInfo());
	}

	@Test
	public void testGetModelType_FixedWing() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).setValue(0x00, 0x04).get();
		HeaderNameBlock headerNameBlock = new HeaderNameBlock(testBlock);
		assertEquals("Fixed Wing", headerNameBlock.get_modelType());
	}

	@Test
	public void testGetModelType_Helicopter() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).setValue(0x01, 0x04).get();
		HeaderNameBlock headerNameBlock = new HeaderNameBlock(testBlock);
		assertEquals("Helicopter", headerNameBlock.get_modelType());
	}

	@Test
	public void testGetModelType_Sailplane() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).setValue(0x02, 0x04).get();
		HeaderNameBlock headerNameBlock = new HeaderNameBlock(testBlock);
		assertEquals("Sailplane", headerNameBlock.get_modelType());
	}

	@Test
	public void testGetModelNumberPos1() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).setValue(0x0000, 0x04).get();
		HeaderNameBlock headerNameBlock = new HeaderNameBlock(testBlock);
		assertEquals(1, headerNameBlock.get_modelNumber());
	}

	@Test
	public void testGetModelNumberPos16() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).setValue(0x0F00, 0x04).get();
		HeaderNameBlock headerNameBlock = new HeaderNameBlock(testBlock);
		assertEquals(16, headerNameBlock.get_modelNumber());
	}

	@Test
	public void testGetModelName() {
		byte[] testBlock = new HeaderBlockBuilder(0, NAME_HEADERBLOCK_MAKER).setValue("ModelName", 0x0C).get();
		HeaderNameBlock headerNameBlock = new HeaderNameBlock(testBlock);
		assertEquals("ModelName", headerNameBlock.get_modelName());
	}

}
