package com.monstarmike.tlmreader.datablock.normalizer.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.monstarmike.tlmreader.datablock.DataBlock;

/**
 * Generic Processor used by normalizers
 */
public class ProcessorEvaluator<T extends DataBlock> {
	private List<AbstractProcessor<T>> processors = new ArrayList<>();

	public void process(List<DataBlock> dataBlocks) {
		evalProcessors(dataBlocks);
	}

	public void registerProcessor(AbstractProcessor<T> processor) {
		processors.add(processor);
	}

	@SuppressWarnings("unchecked")
	private void evalProcessors(List<DataBlock> dataBlocks) {
		for (AbstractProcessor<T> processor : processors) {
			for (DataBlock dataBlock : dataBlocks) {
				if (processor.getClassOfDataBlock().isAssignableFrom(dataBlock.getClass())) {
					processor.preprocess((T) dataBlock);
				}
			}
			processor.preprocessFinished();
			Iterator<DataBlock> iterator = dataBlocks.iterator();
			while (iterator.hasNext()) {
				DataBlock dataBlock = iterator.next();
				if (processor.getClassOfDataBlock().isAssignableFrom(dataBlock.getClass()) && processor.isBad((T) dataBlock)) {
					iterator.remove();
				}
			}
		}
	}
}
