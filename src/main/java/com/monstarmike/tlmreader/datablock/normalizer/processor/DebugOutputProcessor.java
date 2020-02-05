package com.monstarmike.tlmreader.datablock.normalizer.processor;

import com.monstarmike.tlmreader.datablock.RxBlock;

public class DebugOutputProcessor extends AbstractProcessor<RxBlock> {

	RxBlock lastBlock = null;

	public void preprocess(RxBlock rxBlock) {
		if (rxBlock.hasValidDataLostPacketsReceiverA() && rxBlock.getLostPacketsReceiverA() > 200) {
			if (lastBlock != null) {
				System.out.println("LAST: " + lastBlock.getTimestamp() + " " + lastBlock.getLostPacketsReceiverA());
			}
		}
	}

	public Class<RxBlock> getClassOfDataBlock() {
		return RxBlock.class;
	}
}