package com.monstarmike.tlmreader.datablock.normalizer.processor;

import com.monstarmike.tlmreader.datablock.RXBlock;

public class DebugOutputProcessor extends AbstractProcessor<RXBlock>{
	RXBlock lastBlock = null;
	
	public void preprocess(RXBlock rxBlock) {
		if (rxBlock.hasValidDataLostPacketsReceiverA() && rxBlock.getLostPacketsReceiverA() > 200) {
			if (lastBlock != null) {
				System.out.println(
						"LAST: " + lastBlock.getTimestamp() + " " + lastBlock.getLostPacketsReceiverA());
			}
			if (rxBlock != null) {
				System.out.println("rxBlock: " + rxBlock);
			}
		}
	}

	public Class<RXBlock> getClassOfDataBlock() {
		return RXBlock.class;
	}
}