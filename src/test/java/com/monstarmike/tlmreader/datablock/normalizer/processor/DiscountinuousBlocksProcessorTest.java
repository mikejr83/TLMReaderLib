package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.monstarmike.tlmreader.datablock.RXBlock;

public class DiscountinuousBlocksProcessorTest {

	@Test
	public void testShortHighJumpDown() {
		final List<RXBlock> blocks = createBlocks(5000, 234);
		final List<Boolean> expectedBad = Arrays.asList(true, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testLongHighJumpDown() {
		final List<RXBlock> blocks = createBlocks(5000, 5000, 234);
		final List<Boolean> expectedBad = Arrays.asList(true, true, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpShortHigh() {
		final List<RXBlock> blocks = createBlocks(234, 5000);
		final List<Boolean> expectedBad = Arrays.asList(false, true);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpLongHigh() {
		final List<RXBlock> blocks = createBlocks(234, 5000, 5000);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpShortHighJumpDown() {
		final List<RXBlock> blocks = createBlocks(234, 5000, 235);
		final List<Boolean> expectedBad = Arrays.asList(false, true, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);

	}

	@Test
	public void testJumpUpLongHighJumpDown() {
		final List<RXBlock> blocks = createBlocks(234, 5000, 5000, 235);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testShortHighJumpDownJumpUpJumpDown() {
		final List<RXBlock> blocks = createBlocks(5000, 236, 5000, 237);
		final List<Boolean> expectedBad = Arrays.asList(true, false, true, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpJumpDown() {
		final List<RXBlock> blocks = createBlocks(236, 5000, 5000, 5000, 5000, 237, 238, 5000, 5000);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, true, true, false, false, true, true);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpJumpUpJumpDownJumpDown() {
		final List<RXBlock> blocks = createBlocks(236, 5000, 10000, 5000, 237, 238);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, true, false, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testJumpUpJumpUpJumpDownOk() {
		final List<RXBlock> blocks = createBlocks(236, 5000, 10000, 237, 5000);
		final List<Boolean> expectedBad = Arrays.asList(false, true, true, false, true);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testIgnoreInvalidData() {
		final List<RXBlock> blocks = createBlocks(236, 65535, 237);
		Mockito.when(blocks.get(1).hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.FALSE);
		final List<Boolean> expectedBad = Arrays.asList(false, false, false);
		List<Boolean> actualBad = process(new DiscountinuousBlocksProcessor(), blocks);
		Assert.assertEquals(expectedBad, actualBad);
	}

	@Test
	public void testGetClassOfDataBlock() {
		Assert.assertEquals(RXBlock.class, new DiscountinuousBlocksProcessor().getClassOfDataBlock());
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

	private List<RXBlock> createBlocks(final int... values) {
		final List<RXBlock> blocks = new ArrayList<RXBlock>();
		int timestamp = 0;
		for (final int value : values) {
			blocks.add(createRxMock(timestamp++, value));
		}
		return blocks;
	}

	private RXBlock createRxMock(final int timestamp, final int value) {
		final RXBlock mock = Mockito.mock(RXBlock.class);
		Mockito.when(mock.hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.TRUE);
		Mockito.when(mock.getLostPacketsReceiverA()).thenReturn((short) value);
		Mockito.when(mock.getTimestamp()).thenReturn(timestamp);
		return mock;
	}
}
