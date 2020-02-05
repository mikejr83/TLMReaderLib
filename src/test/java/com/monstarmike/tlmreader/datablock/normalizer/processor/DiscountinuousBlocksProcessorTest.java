package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.monstarmike.tlmreader.datablock.RxBlock;

public class DiscountinuousBlocksProcessorTest {

	@Test
	public void testShortHighJumpDown() {
		final List<RxBlock> blocks = createBlocks(5000, 234);
		final List<Boolean> expectedBad = Arrays.asList(true, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testLongHighJumpDown() {
		final List<RxBlock> blocks = createBlocks(5000, 5000, 234);
		final List<Boolean> expectedBad = Arrays.asList(true, true, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpShortHigh() {
		final List<RxBlock> blocks = createBlocks(234, 5000);
		final List<Boolean> expectedBad = Arrays.asList(false, true);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpLongHigh() {
		final List<RxBlock> blocks = createBlocks(234, 5000, 5000);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpShortHighJumpDown() {
		final List<RxBlock> blocks = createBlocks(234, 5000, 235);
		final List<Boolean> expectedBad = Arrays.asList(false, true, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);

	}

	@Test
	public void testJumpUpLongHighJumpDown() {
		final List<RxBlock> blocks = createBlocks(234, 5000, 5000, 235);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testShortHighJumpDownJumpUpJumpDown() {
		final List<RxBlock> blocks = createBlocks(5000, 236, 5000, 237);
		final List<Boolean> expectedBad = Arrays.asList(true, false, true, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpJumpDown() {
		final List<RxBlock> blocks = createBlocks(236, 5000, 5000, 5000, 5000, 237, 238, 5000, 5000);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, true, true, false, false, true, true);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpJumpUpJumpDownJumpDown() {
		final List<RxBlock> blocks = createBlocks(236, 5000, 10000, 5000, 237, 238);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, true, false, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpJumpUpJumpDownOk() {
		final List<RxBlock> blocks = createBlocks(236, 5000, 10000, 237, 5000);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, false, true);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testIgnoreInvalidData() {
		final List<RxBlock> blocks = createBlocks(236, 65535, 237);
		Mockito.when(blocks.get(1).hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.FALSE);
		final List<Boolean> expectedBad = Arrays.asList(false, false, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testGetClassOfDataBlock() {
		Assert.assertEquals(RxBlock.class, new DiscountinuousBlocksProcessor().getClassOfDataBlock());
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

	private List<RxBlock> createBlocks(final int... values) {
		final List<RxBlock> blocks = new ArrayList<RxBlock>();
		int timestamp = 0;
		for (final int value : values) {
			blocks.add(createRxMock(timestamp++, value));
		}
		return blocks;
	}

	private RxBlock createRxMock(final int timestamp, final int value) {
		final RxBlock mock = Mockito.mock(RxBlock.class);
		Mockito.when(mock.hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.TRUE);
		Mockito.when(mock.getLostPacketsReceiverA()).thenReturn((short) value);
		Mockito.when(mock.getTimestamp()).thenReturn(timestamp);
		return mock;
	}
}
