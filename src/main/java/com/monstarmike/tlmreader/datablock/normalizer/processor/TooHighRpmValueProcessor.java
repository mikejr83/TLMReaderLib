package com.monstarmike.tlmreader.datablock.normalizer.processor;

import com.monstarmike.tlmreader.datablock.StandardBlock;

public class TooHighRpmValueProcessor extends AbstractProcessor<StandardBlock> {
	private static final double LIMIT_RPM_VALUE = 10000.0;

	public boolean isBad(StandardBlock block) {
		float rpm = block.getRpm();
		if (rpm > LIMIT_RPM_VALUE) {
			return true;
		}
		return false;
	}

	public Class<? extends StandardBlock> getClassOfDataBlock() {
		return StandardBlock.class;
	}

}
