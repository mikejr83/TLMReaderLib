package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class RXBlockTest {

	private static final short RX_DATABLOCK_MAKER = (short) 0x7F00;

	@Test
	public void testGetLostPacketsReceiverA() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(123, 0x06).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(123, (short) rxBlock.getLostPacketsReceiverA());
	}
	
	@Test
	public void testHasValidDataLostPacketsReceiverAUnsignedShortMax() {
		int unsignedShortMax = 0xFFFF;
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(unsignedShortMax, 0x06).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertFalse(rxBlock.hasValidDataLostPacketsReceiverA());
	}
	
	@Test
	public void testHasValidDataLostPacketsReceiverASignedShortMax() {
		int signedShortMax = Short.MAX_VALUE+1;
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(signedShortMax, 0x06).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertFalse(rxBlock.hasValidDataLostPacketsReceiverA());
	}
	
	@Test
	public void testHasValidDataLostPacketsReceiverAOk() {
		int signedShortMax = 0;
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(signedShortMax, 0x06).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertTrue(rxBlock.hasValidDataLostPacketsReceiverA());
	}

	@Test
	public void testGetLostPacketsReceiverB() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(739, 0x08).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertTrue(rxBlock.hasValidDataLostPacketsReceiverB());
		assertEquals(739, (short) rxBlock.getLostPacketsReceiverB());
	}

	@Test
	public void testGetLostPacketsReceiverL() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(999, 0x0A).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertTrue(rxBlock.hasValidDataLostPacketsReceiverL());
		assertEquals(999, (short) rxBlock.getLostPacketsReceiverL());
	}

	@Test
	public void testGetLostPacketsReceiverR() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(302, 0x0C).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertTrue(rxBlock.hasValidDataLostPacketsReceiverR());
		assertEquals(302, (short) rxBlock.getLostPacketsReceiverR());
	}

	@Test
	public void testGetFrameLoss() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(3, 0x0E).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertTrue(rxBlock.hasValidFrameLosssData());
		assertEquals(3, (short) rxBlock.getFrameLoss());
	}

	@Test
	public void testGetHolds() {
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(15, 0x10).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertTrue(rxBlock.hasValidHoldsData());
		assertEquals(15, (short) rxBlock.getHolds());
	}

	@Test
	public void testVolts() {
		int valueInHunderthOfVolt = 603;
		byte[] testBlock = new DataBlockBuilder(0, RX_DATABLOCK_MAKER).setValue(valueInHunderthOfVolt, 0x12).get();
		RXBlock rxBlock = (RXBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(valueInHunderthOfVolt, rxBlock.getVoltageInHunderthOfVolts());
	}
}
