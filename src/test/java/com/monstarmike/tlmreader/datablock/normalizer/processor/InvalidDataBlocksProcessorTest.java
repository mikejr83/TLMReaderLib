package com.monstarmike.tlmreader.datablock.normalizer.processor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.monstarmike.tlmreader.datablock.RxBlock;

@RunWith(MockitoJUnitRunner.class)
public class InvalidDataBlocksProcessorTest {

	@Mock
	private RxBlock validRxBlock;
	@Mock
	private RxBlock invalidRxBlock;

	@Before
	public void setup() {
		Mockito.when(validRxBlock.hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.TRUE);
	}

	@Test
	public void testIsBadValidBlock() {
		InvalidDataBlocksProcessor processor = new InvalidDataBlocksProcessor();
		Assert.assertFalse(processor.isBad(validRxBlock));
	}

	@Test
	public void testIsBadInvalidBlock() {
		InvalidDataBlocksProcessor processor = new InvalidDataBlocksProcessor();
		Assert.assertTrue(processor.isBad(invalidRxBlock));
	}
	
	@Test
	public void testGetClassOfDataBlock() {
		InvalidDataBlocksProcessor processor = new InvalidDataBlocksProcessor();
		processor.preprocess(null);
		processor.preprocessFinished();
		Assert.assertEquals(RxBlock.class, processor.getClassOfDataBlock());
	}

}
