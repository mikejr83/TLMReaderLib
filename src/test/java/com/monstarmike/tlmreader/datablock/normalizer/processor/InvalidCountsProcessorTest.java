package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.monstarmike.tlmreader.datablock.RxBlock;

public class InvalidCountsProcessorTest {

	@Test
	public void testSpikeLessThanTenPercentBLR() {
		boolean[] normal = new boolean[] { true, false, false, false };
		boolean[] spike = new boolean[] { true, true, true, true };
		final List<RxBlock> blocks = createBlocks(normal, normal, normal, normal, normal, normal, normal, spike, normal,
				normal, normal);
		final List<Boolean> expectedBad = Arrays.asList(false, false, false, false, false, false, false, true, false,
				false, false);
		List<Boolean> actualBad = process(new InvalidCountsProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}
	
	@Test
	public void testSpikeLessThanTenPercentA() {
		boolean[] normal = new boolean[] { false, true,true,true };
		boolean[] spike = new boolean[] { true, true, true, true };
		final List<RxBlock> blocks = createBlocks(normal, normal, normal, normal, normal, normal, normal, spike, normal,
				normal, normal);
		final List<Boolean> expectedBad = Arrays.asList(false, false, false, false, false, false, false, true, false,
				false, false);
		List<Boolean> actualBad = process(new InvalidCountsProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testSpikesMoreThanTenPercent() {
		boolean[] normal = new boolean[] { true, false, false, false };
		boolean[] spike = new boolean[] { true, true, true, true };
		final List<RxBlock> blocks = createBlocks(normal, normal, normal, normal, normal, normal, normal, spike);
		final List<Boolean> expectedBad = Arrays.asList(false, false, false, false, false, false, false, false);
		List<Boolean> actualBad = process(new InvalidCountsProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}
	
	@Test
	public void testIgnoreInvalidData() {
		boolean[] normal = new boolean[] { true, false, false, false };
		boolean[] invalid = new boolean[] { false, false, false, false };
		final List<RxBlock> blocks = createBlocks(normal, normal, invalid, normal);
		final List<Boolean> expectedBad = Arrays.asList(false, false, true, false);
		List<Boolean> actualBad = process(new InvalidCountsProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testGetClassOfDataBlock() {
		Assert.assertEquals(RxBlock.class, new InvalidCountsProcessor().getClassOfDataBlock());
	}
	
	private List<Boolean> process(final AbstractProcessor<RxBlock> processor, final List<RxBlock> blocks) {
		for (RxBlock block : blocks) {
			processor.preprocess(block);
		}
		processor.preprocessFinished();
		List<Boolean> actual = new ArrayList<Boolean>();
		for (RxBlock block : blocks) {
			actual.add(processor.isBad(block));
		}
		return actual;
	}

	private List<RxBlock> createBlocks(final boolean[]... hasValidDataList) {
		final List<RxBlock> blocks = new ArrayList<RxBlock>();
		int timestamp = 0;
		for (final boolean[] hasValidDatas : hasValidDataList) {
			blocks.add(createRxMock(timestamp++, hasValidDatas));
		}
		return blocks;
	}

	private RxBlock createRxMock(final int timestamp, final boolean[] hasValidDatas) {
		final RxBlock mock = Mockito.mock(RxBlock.class);
		Mockito.when(mock.hasValidDataLostPacketsReceiverA()).thenReturn(hasValidDatas[0]);
		Mockito.when(mock.hasValidDataLostPacketsReceiverB()).thenReturn(hasValidDatas[1]);
		Mockito.when(mock.hasValidDataLostPacketsReceiverL()).thenReturn(hasValidDatas[2]);
		Mockito.when(mock.hasValidDataLostPacketsReceiverR()).thenReturn(hasValidDatas[3]);
		Mockito.when(mock.getTimestamp()).thenReturn(timestamp);
		return mock;
	}
}
