package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.monstarmike.tlmreader.datablock.RxBlock;

public class SpectrumTelemetryDetectorTest {

	@Test
	public void testIsSpectrumTelemetry() {
		final List<RxBlock> blocks = createBlocks(0, 1, 1, 7, 10, 500, 10);
		SpectrumTelemetryDetector processor = new SpectrumTelemetryDetector();
		process(processor, blocks);
		Assert.assertTrue(processor.isSpectrumTelemetry());
	}

	@Test
	public void testIsNotSpectrumTelemetry() {
		final List<RxBlock> blocks = createBlocks(100,100,95,90,100,95,40,30,90,100);
		SpectrumTelemetryDetector processor = new SpectrumTelemetryDetector();
		process(processor, blocks);
		Assert.assertFalse(processor.isSpectrumTelemetry());
	}
	
	@Test
	public void testIsNotSpectrumTelemetryIgnoreInvalid() {
		final List<RxBlock> blocks = createBlocks(-1,-1,-1,-1,-1,-1,100,70,90,100);
		Mockito.when(blocks.get(0).hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.FALSE);
		Mockito.when(blocks.get(1).hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.FALSE);
		Mockito.when(blocks.get(2).hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.FALSE);
		Mockito.when(blocks.get(3).hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.FALSE);
		Mockito.when(blocks.get(4).hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.FALSE);
		Mockito.when(blocks.get(5).hasValidDataLostPacketsReceiverA()).thenReturn(Boolean.FALSE);
		SpectrumTelemetryDetector processor = new SpectrumTelemetryDetector();
		process(processor, blocks);
		Assert.assertFalse(processor.isSpectrumTelemetry());
	}

	@Test
	public void testGetClassOfDataBlock() {
		Assert.assertEquals(RxBlock.class, new SpectrumTelemetryDetector().getClassOfDataBlock());
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
