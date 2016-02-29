package com.monstarmike.tlmreader.datablock.normalizer.processor;

import com.monstarmike.tlmreader.datablock.RXBlock;

public class InvalidDataBlocksProcessor extends AbstractProcessor<RXBlock> {

	public boolean isBad(RXBlock block) {
		return !block.hasValidDataLostPacketsReceiverA();
	}

	public Class<RXBlock> getClassOfDataBlock() {
		return RXBlock.class;
	}
}