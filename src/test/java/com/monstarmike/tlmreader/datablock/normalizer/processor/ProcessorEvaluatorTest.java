package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.monstarmike.tlmreader.DataBlockBuilder;
import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.RxBlock;
import com.monstarmike.tlmreader.datablock.VoltageBlock;

@RunWith(MockitoJUnitRunner.class)
public class ProcessorEvaluatorTest {

	private static final int POS_A = 0x06;

	@Mock
	AbstractProcessor<RxBlock> processorMock;

	@Test
	public void testProcessWithRxTypeDataBlocks() {
		Mockito.when(processorMock.getClassOfDataBlock()).thenAnswer(new Answer<Class<? extends RxBlock>>() {

			public Class<? extends RxBlock> answer(InvocationOnMock invocation) throws Throwable {
				return RxBlock.class;
			}
		});;
		List<DataBlock> dataBlocks = new ArrayList<DataBlock>();
		dataBlocks.add(createRx(0, 65535));
		dataBlocks.add(createRx(1, 234));
		ProcessorEvaluator<RxBlock> genericProcessor = new ProcessorEvaluator<RxBlock>();
		genericProcessor.registerProcessor(processorMock);
		genericProcessor.process(dataBlocks);
		Mockito.verify(processorMock, Mockito.times(2)).preprocess(Mockito.any(RxBlock.class));;
		Mockito.verify(processorMock, Mockito.times(2)).isBad(Mockito.any(RxBlock.class));;
		Mockito.verify(processorMock, Mockito.times(1)).preprocessFinished();
	}
	
	@Test
	public void testProcessWithMixedTypeDataBlocks() {
		Mockito.when(processorMock.getClassOfDataBlock()).thenAnswer(new Answer<Class<? extends RxBlock>>() {
			
			public Class<? extends RxBlock> answer(InvocationOnMock invocation) throws Throwable {
				return RxBlock.class;
			}
		});;
		List<DataBlock> dataBlocks = new ArrayList<DataBlock>();
		dataBlocks.add(createRx(0, 65535));
		dataBlocks.add(createVoltage(1));
		ProcessorEvaluator<RxBlock> genericProcessor = new ProcessorEvaluator<RxBlock>();
		genericProcessor.registerProcessor(processorMock);
		genericProcessor.process(dataBlocks);
		Mockito.verify(processorMock, Mockito.times(1)).preprocess(Mockito.any(RxBlock.class));;
		Mockito.verify(processorMock, Mockito.times(1)).isBad(Mockito.any(RxBlock.class));;
		Mockito.verify(processorMock, Mockito.times(1)).preprocessFinished();
	}

	@Test
	public void testProcessCheckIsBad() {
		Mockito.when(processorMock.getClassOfDataBlock()).thenAnswer(new Answer<Class<? extends RxBlock>>() {
			
			public Class<? extends RxBlock> answer(InvocationOnMock invocation) throws Throwable {
				return RxBlock.class;
			}
		});;
		Mockito.when(processorMock.isBad(Mockito.any(RxBlock.class))).thenReturn(Boolean.TRUE);
		List<DataBlock> dataBlocks = new ArrayList<DataBlock>();
		dataBlocks.add(createRx(0, 65535));
		ProcessorEvaluator<RxBlock> genericProcessor = new ProcessorEvaluator<RxBlock>();
		genericProcessor.registerProcessor(processorMock);
		genericProcessor.process(dataBlocks);
		Mockito.verify(processorMock, Mockito.times(1)).preprocess(Mockito.any(RxBlock.class));;
		Mockito.verify(processorMock, Mockito.times(1)).isBad(Mockito.any(RxBlock.class));;
		Mockito.verify(processorMock, Mockito.times(1)).preprocessFinished();
		Assert.assertTrue(dataBlocks.isEmpty());
	}
	
	private DataBlock createRx(int timestamp, int a) {
		final byte[] testBlock = new DataBlockBuilder(timestamp, 0).setValue(a, POS_A).get();
		return new RxBlock(testBlock);
	}

	private DataBlock createVoltage(int timestamp) {
		final byte[] testBlock = new DataBlockBuilder(timestamp, 0).get();
		return new VoltageBlock(testBlock);
	}
}
