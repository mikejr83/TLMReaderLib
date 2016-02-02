package com.monstarmike.tlmreader.datablock;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstarmike.tlmreader.DataBlockBuilder;

public class CurrentBlockTest {

	private static final short CURRENT_DATABLOCK_MAKER = (short) 0x0300;

	@Test
	public void testGetCurrent() {
		byte[] testBlock = new DataBlockBuilder(0, CURRENT_DATABLOCK_MAKER).setValue(2973, 0x6).get();
		CurrentBlock currentBlock = (CurrentBlock) DataBlock.createDataBlock(testBlock, null);
		assertEquals(2973.0 * 0.1967 , currentBlock.get_Current(), 0.0001);
	}


}
