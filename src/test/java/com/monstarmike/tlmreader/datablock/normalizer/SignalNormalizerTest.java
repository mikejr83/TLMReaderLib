package com.monstarmike.tlmreader.datablock.normalizer;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.monstarmike.tlmreader.DataBlockBuilder;
import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderRxBlock;
import com.monstarmike.tlmreader.datablock.RXBlock;

public class SignalNormalizerTest {

	private static final int POS_A = 0x06;
	private HeaderRxBlock rxHeader;

	@Before
	public void setup() {
		rxHeader = mock(HeaderRxBlock.class);
	}

	@Test
	public void testNormalize() {
		SignalNormalizer normalizer = new SignalNormalizer(rxHeader);
		final List<DataBlock> blocks = createBlocks(0, 0, 0, 0, 1);
		normalizer.normalize(blocks);
		Mockito.verify(rxHeader).setSpectrumTelemetrySystem(true);
	}

	private List<DataBlock> createBlocks(final int... values) {
		final List<DataBlock> blocks = new ArrayList<DataBlock>();
		int timestamp = 0;
		for (final int value : values) {
			blocks.add(createRx(timestamp++, value));
		}
		return blocks;
	}
	
	private DataBlock createRx(int timestamp, int a) {
		final byte[] testBlock = new DataBlockBuilder(timestamp, 0).setValue(a, POS_A).get();
		return new RXBlock(testBlock);
	}

}
