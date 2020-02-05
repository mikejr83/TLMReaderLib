package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.RxBlock;
import com.monstarmike.tlmreader.datablock.StandardBlock;

public class RedundantDataBlocksProcessorTest {

	@Test
	public void testRedundantValues() { 
		final List<DataBlock> blocks = createBlocks(false, true, true, true, true, false, true, true, true, false, true);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, true, false, false, true, true, false, false,
				false);
		List<Boolean> actualBad = process(new RedundantDataBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testRedundantValuesAllEquals() { 
		final List<DataBlock> blocks = createBlocks(false, true, true, true, true, true, true, true, true, true, true);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, true, true, true, true, true, true, true,
				false);
		List<Boolean> actualBad = process(new RedundantDataBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}
	
	@Test
	public void testGetClassOfDataBlock() {
		Assert.assertEquals(StandardBlock.class, new TooHighRpmValueProcessor().getClassOfDataBlock());
	}

	private List<Boolean> process(final RedundantDataBlocksProcessor processor, final List<DataBlock> blocks) {
		for (DataBlock block : blocks) {
			processor.preprocess(block);
		}
		processor.preprocessFinished();
		List<Boolean> actual = new ArrayList<Boolean>();
		for (DataBlock block : blocks) {
			actual.add(processor.isBad(block));
		}
		return actual;
	}

	private List<DataBlock> createBlocks(final boolean... equalsToLastList) {
		final List<DataBlock> blocks = new ArrayList<DataBlock>();
		int timestamp = 0;
		for (final boolean equalsToLast : equalsToLastList) {
			blocks.add(createRxMock(timestamp++, equalsToLast));
		}
		return blocks;
	}

	private DataBlock createRxMock(final int timestamp, final boolean equalsToLast) {
		final DataBlock mock = Mockito.mock(RxBlock.class);
		Mockito.when(mock.areValuesEquals(Mockito.any(DataBlock.class))).thenReturn(equalsToLast);
		Mockito.when(mock.getTimestamp()).thenReturn(timestamp);
		Mockito.when(mock.getSequence()).thenReturn(timestamp);
		return mock;
	}
}
