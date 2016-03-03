package com.monstarmike.tlmreader.datablock.normalizer;

import java.util.List;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.StandardBlock;
import com.monstarmike.tlmreader.datablock.normalizer.processor.ProcessorEvaluator;
import com.monstarmike.tlmreader.datablock.normalizer.processor.TooHighRpmValueProcessor;

public class RpmNormalizer implements DataNormalizer {

	/**
	 * Remove all datablocks with too high rpm values
	 */
	public void normalize(final List<DataBlock> dataBlocks) {
		ProcessorEvaluator<StandardBlock> evaluator = new ProcessorEvaluator<>();
		evaluator.registerProcessor(new TooHighRpmValueProcessor());
		evaluator.process(dataBlocks);
	}
}
