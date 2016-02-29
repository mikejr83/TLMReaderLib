package com.monstarmike.tlmreader.datablock.normalizer.processor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.RXBlock;
import com.monstarmike.tlmreader.datablock.StandardBlock;

public class RedundantDataBlocksProcessorTest {

	@Test
	public void testRedundantValues() { 
		final List<RXBlock> blocks = createBlocks(false, true, true, true, true, false, true, true, true, false, true);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, true, false, false, true, true, false, false,
				false);
		List<Boolean> actualBad = process(new RedundantDataBlocksProcessor<RXBlock>(){

			@Override
			public Class<? extends RXBlock> getClassOfDataBlock() {
				return RXBlock.class;
			}
			
		}, blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testGetClassOfDataBlock() {
		Assert.assertEquals(StandardBlock.class, new TooHighRpmValueProcessor().getClassOfDataBlock());
	}

	private List<Boolean> process(final AbstractProcessor<RXBlock> processor, final List<RXBlock> blocks) {
		for (RXBlock block : blocks) {
			processor.preprocess(block);
		}
		processor.preprocessFinished();
		List<Boolean> actual = new ArrayList<Boolean>();
		for (RXBlock block : blocks) {
			actual.add(processor.isBad(block));
		}
		return actual;
	}

	private List<RXBlock> createBlocks(final boolean... equalsToLastList) {
		final List<RXBlock> blocks = new ArrayList<RXBlock>();
		int timestamp = 0;
		for (final boolean equalsToLast : equalsToLastList) {
			blocks.add(createRxMock(timestamp++, equalsToLast));
		}
		return blocks;
	}

	private RXBlock createRxMock(final int timestamp, final boolean equalsToLast) {
		final RXBlock mock = Mockito.mock(RXBlock.class);
		Mockito.when(mock.areValuesEquals(Mockito.any(DataBlock.class))).thenReturn(equalsToLast);
		Mockito.when(mock.getTimestamp()).thenReturn(timestamp);
		return mock;
	}
}
