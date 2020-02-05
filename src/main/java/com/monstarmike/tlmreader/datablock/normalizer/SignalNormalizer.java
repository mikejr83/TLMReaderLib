package com.monstarmike.tlmreader.datablock.normalizer;

import java.util.List;

import com.monstarmike.tlmreader.datablock.DataBlock;
import com.monstarmike.tlmreader.datablock.HeaderRxBlock;
import com.monstarmike.tlmreader.datablock.RxBlock;
import com.monstarmike.tlmreader.datablock.normalizer.processor.DebugOutputProcessor;
import com.monstarmike.tlmreader.datablock.normalizer.processor.DiscountinuousBlocksProcessor;
import com.monstarmike.tlmreader.datablock.normalizer.processor.InvalidCountsProcessor;
import com.monstarmike.tlmreader.datablock.normalizer.processor.InvalidDataBlocksProcessor;
import com.monstarmike.tlmreader.datablock.normalizer.processor.ProcessorEvaluator;
import com.monstarmike.tlmreader.datablock.normalizer.processor.SpectrumTelemetryDetector;

public class SignalNormalizer implements DataNormalizer {

	private final HeaderRxBlock headerRxBlock;

	public SignalNormalizer(final HeaderRxBlock headerRxBlock) {
		this.headerRxBlock = headerRxBlock;
	}

	public void normalize(final List<DataBlock> dataBlocks) {
		ProcessorEvaluator<RxBlock> evaluator = new ProcessorEvaluator<>();
		evaluator.registerProcessor(new DebugOutputProcessor());
		evaluator.registerProcessor(new InvalidDataBlocksProcessor());
		evaluator.registerProcessor(new DiscountinuousBlocksProcessor());
		evaluator.registerProcessor(new InvalidCountsProcessor());
		final SpectrumTelemetryDetector detectSpectrumTelemetry = new SpectrumTelemetryDetector();
		evaluator.registerProcessor(detectSpectrumTelemetry);
		evaluator.process(dataBlocks);
		
		headerRxBlock.setSpectrumTelemetrySystem(detectSpectrumTelemetry.isSpectrumTelemetry());
	}
}
