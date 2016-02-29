package com.monstarmike.tlmreader.datablock.normalizer.processor;

import com.monstarmike.tlmreader.datablock.RXBlock;

public class InvalidCountsProcessor extends AbstractProcessor<RXBlock> {
	private double lessThan10Percent = 0.1;
	private int countA = 0;
	private int countB = 0;
	private int countL = 0;
	private int countR = 0;
	private int totalCount = 0;

	public void preprocess(RXBlock rxBlock) {
		if (rxBlock.hasValidDataLostPacketsReceiverA()) {
			countA++;
		}
		if (rxBlock.hasValidDataLostPacketsReceiverB()) {
			countB++;
		}
		if (rxBlock.hasValidDataLostPacketsReceiverL()) {
			countL++;
		}
		if (rxBlock.hasValidDataLostPacketsReceiverR()) {
			countR++;
		}
		totalCount++;
	}

	public boolean isBad(RXBlock rxBlock) {
		boolean remove = false;
		boolean badBlock = true;
		if (rxBlock.hasValidDataLostPacketsReceiverA()) {
			if (calculatePercent(countA) < lessThan10Percent) {
				remove = true;
			}
			badBlock = false;
		}
		if (rxBlock.hasValidDataLostPacketsReceiverB()) {
			if (calculatePercent(countB) < lessThan10Percent) {
				remove = true;
			}
			badBlock = false;
		}
		if (rxBlock.hasValidDataLostPacketsReceiverL()) {
			if (calculatePercent(countL) < lessThan10Percent) {
				remove = true;
			}
			badBlock = false;
		}
		if (rxBlock.hasValidDataLostPacketsReceiverR()) {
			if (calculatePercent(countR) < lessThan10Percent) {
				remove = true;
			}
			badBlock = false;
		}
		if (badBlock || remove) {
			return true;
		}
		return false;
	}

	public Class<RXBlock> getClassOfDataBlock() {
		return RXBlock.class;
	}

	private double calculatePercent(int count) {
		return (double) count / totalCount;
	}

}
