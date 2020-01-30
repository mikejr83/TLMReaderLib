package com.monstarmike.tlmreader.datablock.normalizer.processor;

import com.monstarmike.tlmreader.datablock.RxBlock;

public class InvalidDataBlocksProcessor extends AbstractProcessor<RxBlock> {

	public boolean isBad(RxBlock block) {
		return !block.hasValidDataLostPacketsReceiverA();
	}

	public Class<RxBlock> getClassOfDataBlock() {
		return RxBlock.class;
	}
}