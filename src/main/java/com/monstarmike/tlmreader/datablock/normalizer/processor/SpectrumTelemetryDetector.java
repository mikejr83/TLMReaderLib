package com.monstarmike.tlmreader.datablock.normalizer.processor;

import com.monstarmike.tlmreader.datablock.RxBlock;
import com.monstarmike.tlmreader.datablock.normalizer.util.MyFrequency;

public class SpectrumTelemetryDetector extends AbstractProcessor<RxBlock> {
	final MyFrequency frequencyA = new MyFrequency();

	public boolean isSpectrumTelemetry() {
		// if Spectrum, the most values will be between 0 und 50 lostPackets
		final long belowOrEquals50 = frequencyA.getCumFreq(50);
		final long belowOrEquals100 = frequencyA.getCumFreq(100);
		// if Lemon, the most values will be between 100 und 50
		final long between50And100 = belowOrEquals100 - belowOrEquals50;

		return belowOrEquals50 > between50And100;
	}

	public void preprocess(RxBlock dataBlock) {
		if (dataBlock.hasValidDataLostPacketsReceiverA()) {
			frequencyA.addValue(dataBlock.getLostPacketsReceiverA());
		}
	}

	public Class<RxBlock> getClassOfDataBlock() {
		return RxBlock.class;
	}
}
