package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.monstarmike.tlmreader.datablock.StandardBlock;

public class TooHighRpmValueProcessorTest {

	@Test
	public void testAllDataOk() {
		final List<StandardBlock> blocks = createBlocks(0f, 50f, 100f, 300f, 1000f, 1500f, 2000f, 2500f, 3000f);
		final List<Boolean> expectedBad = Arrays.asList(false, false, false, false, false, false, false, false, false);
		List<Boolean> actualBad = process(new TooHighRpmValueProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}
	
	
	@Test
	public void testTooHighValues() {
		final List<StandardBlock> blocks = createBlocks(0f, 50f, 100f, 300f, 1000f, 30000f, 1500f, 2000f, 2500f, 3000f);
		final List<Boolean> expectedBad = Arrays.asList(false, false, false, false, false, true, false, false, false, false);
		List<Boolean> actualBad = process(new TooHighRpmValueProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testGetClassOfDataBlock() {
		Assert.assertEquals(StandardBlock.class, new TooHighRpmValueProcessor().getClassOfDataBlock());
	}

	private List<Boolean> process(final AbstractProcessor<StandardBlock> processor, final List<StandardBlock> blocks) {
		for (StandardBlock block : blocks) {
			processor.preprocess(block);
		}
		processor.preprocessFinished();
		List<Boolean> actual = new ArrayList<Boolean>();
		for (StandardBlock block : blocks) {
			actual.add(processor.isBad(block));
		}
		return actual;
	}

	private List<StandardBlock> createBlocks(final float... values) {
		final List<StandardBlock> blocks = new ArrayList<StandardBlock>();
		int timestamp = 0;
		for (final float value : values) {
			blocks.add(createStandardMock(timestamp++, value));
		}
		return blocks;
	}

	private StandardBlock createStandardMock(final int timestamp, final float value) {
		final StandardBlock mock = Mockito.mock(StandardBlock.class);
		Mockito.when(mock.hasValidRpmData()).thenReturn(Boolean.TRUE);
		Mockito.when(mock.getRpm()).thenReturn(value);
		Mockito.when(mock.getTimestamp()).thenReturn(timestamp);
		return mock;
	}

}
